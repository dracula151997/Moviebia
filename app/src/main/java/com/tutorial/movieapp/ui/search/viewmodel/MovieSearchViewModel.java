package com.tutorial.movieapp.ui.search.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.tutorial.movieapp.local.dao.MovieDao;
import com.tutorial.movieapp.local.entity.MovieEntity;
import com.tutorial.movieapp.remote.MovieApiService;
import com.tutorial.movieapp.remote.Resource;
import com.tutorial.movieapp.repository.MovieRepository;
import com.tutorial.movieapp.ui.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

public class MovieSearchViewModel extends BaseViewModel
{
    private MovieRepository movieRepository;
    private MutableLiveData<Resource<List<MovieEntity>>> moviesLiveData = new MutableLiveData<>();

    @Inject
    public MovieSearchViewModel(MovieApiService apiService, MovieDao dao)
    {
        movieRepository = new MovieRepository(apiService, dao);
    }

    public void searchMovie(String query)
    {
        movieRepository.searchForMovie(query, 1L)
                .doOnSubscribe(disposable -> addToDisposable(disposable))
                .subscribe(data -> getMoviesLiveData().postValue(data));
    }

    public MutableLiveData<Resource<List<MovieEntity>>> getMoviesLiveData()
    {
        return moviesLiveData;
    }
}
