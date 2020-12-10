package com.tutorial.movieapp.ui.main.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.tutorial.movieapp.local.dao.MovieDao;
import com.tutorial.movieapp.local.entity.MovieEntity;
import com.tutorial.movieapp.remote.MovieApiService;
import com.tutorial.movieapp.remote.Resource;
import com.tutorial.movieapp.repository.MovieRepository;
import com.tutorial.movieapp.ui.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

public class MovieListViewModel extends BaseViewModel
{

    private final MovieRepository movieRepository;
    private final MutableLiveData<Resource<List<MovieEntity>>> movieListLiveData = new MutableLiveData<>();
    private String type;

    @Inject
    public MovieListViewModel(MovieDao movieDao, MovieApiService apiService)
    {
        movieRepository = new MovieRepository(apiService, movieDao);
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void fetchMovies(Long currentPage)
    {
        movieRepository.fetchMovieByType(currentPage, type)
                .doOnSubscribe(this::addToDisposable)
                .subscribe(response -> getMoviesLiveData().postValue(response));
    }

    public boolean isLastPage()
    {
        return (movieListLiveData != null && !movieListLiveData.getValue().data.isEmpty()) && movieListLiveData.getValue().data.get(0).isLastPage();
    }

    public MutableLiveData<Resource<List<MovieEntity>>> getMoviesLiveData()
    {
        return movieListLiveData;
    }

}
