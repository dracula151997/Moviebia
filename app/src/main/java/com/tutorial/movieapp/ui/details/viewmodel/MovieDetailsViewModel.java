package com.tutorial.movieapp.ui.details.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.tutorial.movieapp.local.dao.MovieDao;
import com.tutorial.movieapp.local.entity.MovieEntity;
import com.tutorial.movieapp.remote.MovieApiService;
import com.tutorial.movieapp.remote.Resource;
import com.tutorial.movieapp.repository.MovieRepository;
import com.tutorial.movieapp.ui.base.BaseViewModel;

import javax.inject.Inject;

public class MovieDetailsViewModel extends BaseViewModel
{
    private final MovieRepository movieRepository;

    private final MutableLiveData<Resource<MovieEntity>> movieDetailLiveData = new MutableLiveData<>();


    @Inject
    public MovieDetailsViewModel(MovieApiService apiService, MovieDao movieDao)
    {
        movieRepository = new MovieRepository(apiService, movieDao);
    }


    public void fetchMovieDetailsById(Long movieId)
    {
        movieRepository.getMovieDetailsById(movieId)
                .doOnSubscribe(this::addToDisposable)
                .subscribe(data -> getMovieDetailLiveData().postValue(data));
    }

    public MutableLiveData<Resource<MovieEntity>> getMovieDetailLiveData()
    {
        return movieDetailLiveData;
    }

}
