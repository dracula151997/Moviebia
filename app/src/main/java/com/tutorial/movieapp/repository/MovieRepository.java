package com.tutorial.movieapp.repository;

import com.tutorial.movieapp.local.dao.MovieDao;
import com.tutorial.movieapp.local.entity.MovieEntity;
import com.tutorial.movieapp.remote.MovieApiService;
import com.tutorial.movieapp.remote.NetworkBoundResource;
import com.tutorial.movieapp.remote.Resource;
import com.tutorial.movieapp.remote.model.MovieApiResponse;
import com.tutorial.movieapp.utils.AppUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Observable;

@Singleton
public class MovieRepository
{
    private final MovieApiService apiService;
    private final MovieDao movieDao;

    public MovieRepository(MovieApiService apiService, MovieDao movieDao)
    {
        this.apiService = apiService;
        this.movieDao = movieDao;
    }

    public Observable<Resource<List<MovieEntity>>> fetchMovieByType(Long page, String type)
    {
        return new NetworkBoundResource<List<MovieEntity>, MovieApiResponse>()
        {
            @Override
            protected void saveCallResult(MovieApiResponse response)
            {
                //TODO create en empty list for adding a new entity
                List<MovieEntity> movieEntities = new ArrayList<>();
                //TODO iterate through response results
                for (MovieEntity movieEntity : response.getResults())
                {
                    //TODO get the cached movie before from db by movie id (is this movie stored before?)
                    MovieEntity storedMovie = movieDao.getMovieById(movieEntity.getId());
                    //TODO if no
                    if (storedMovie == null)
                    {
                        //TODO save the type of the movie in the entity list
                        movieEntity.setCategoryTypes(Collections.singletonList(type));
                    } else
                    {
                        //TODO otherwise, get the category types for cached movie
                        List<String> categories = storedMovie.getCategoryTypes();
                        //TODO add the new type of its category
                        categories.add(type);
                        movieEntity.setCategoryTypes(categories);
                    }

                    //TODO save the page and total page for the response and then add it to a list
                    movieEntity.setPage(response.getPage());
                    movieEntity.setTotalPages(response.getTotalPages());
                    movieEntities.add(movieEntity);
                }

                //TODO insert the movies
                movieDao.insertAll(movieEntities);
            }

            @Override
            protected boolean shouldFetch()
            {
                return true;
            }

            @Override
            protected Flowable<List<MovieEntity>> loadFromDb()
            {
                //TODO get movies by the page
                List<MovieEntity> movieEntities = movieDao.getMoviesByPage(page);
                if (movieEntities == null || movieEntities.isEmpty())
                {
                    return Flowable.empty();
                }
                return Flowable.just(AppUtils.getMoviesByType(type, movieEntities));
            }

            @Override
            protected Observable<Resource<MovieApiResponse>> createCall()
            {
                return apiService.fetchMoviesByType(type, page)
                        .flatMap(response -> Observable.just(response == null ?
                                Resource.error("", new MovieApiResponse()) :
                                Resource.success(response)));
            }
        }.getAsObservable();
    }

    public Observable<Resource<MovieEntity>> getMovieDetailsById(Long movieId)
    {
        return new NetworkBoundResource<MovieEntity, MovieEntity>()
        {
            @Override
            protected void saveCallResult(MovieEntity item)
            {
                MovieEntity storedMovieEntity = movieDao.getMovieById(movieId);
                if (storedMovieEntity == null) movieDao.insert(item);
                else movieDao.update(item);
            }

            @Override
            protected boolean shouldFetch()
            {
                return true;
            }

            @Override
            protected Flowable<MovieEntity> loadFromDb()
            {
                MovieEntity movieEntity = movieDao.getMovieById(movieId);
                if (movieEntity == null) return Flowable.empty();
                else return Flowable.just(movieEntity);
            }

            @Override
            protected Observable<Resource<MovieEntity>> createCall()
            {
                String id = String.valueOf(movieId);
                return Observable.combineLatest(apiService.fetchMovieDetail(id),
                        apiService.fetchMovieReviews(id),
                        apiService.fetchMovieVideo(id),
                        apiService.fetchSimilarMovie(id, 1),
                        apiService.fetchCastDetail(id),
                        (movieEntity, reviewResponse, videoResponse, movieResponse, creditResponse) ->
                        {
                            if (videoResponse != null)
                                movieEntity.setVideos(videoResponse.getResults());

                            if (creditResponse != null)
                            {
                                movieEntity.setCrews(creditResponse.getCrew());
                                movieEntity.setCasts(creditResponse.getCast());
                            }

                            if (movieResponse != null)
                                movieEntity.setSimilarMovies(movieResponse.getResults());

                            if (reviewResponse != null)
                                movieEntity.setReviews(reviewResponse.getResults());

                            return Resource.success(movieEntity);
                        });
            }
        }.getAsObservable();
    }
}
