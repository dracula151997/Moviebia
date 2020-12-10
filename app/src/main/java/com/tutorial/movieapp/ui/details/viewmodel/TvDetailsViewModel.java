package com.tutorial.movieapp.ui.details.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.tutorial.movieapp.local.dao.TvDao;
import com.tutorial.movieapp.local.entity.TvEntity;
import com.tutorial.movieapp.remote.Resource;
import com.tutorial.movieapp.remote.TvApiService;
import com.tutorial.movieapp.repository.TvRepository;
import com.tutorial.movieapp.ui.base.BaseViewModel;

import javax.inject.Inject;

public class TvDetailsViewModel extends BaseViewModel
{
    private final TvRepository tvRepository;
    private final MutableLiveData<Resource<TvEntity>> tvDetailsLiveData = new MutableLiveData<>();

    @Inject
    public TvDetailsViewModel(TvApiService apiService, TvDao dao)
    {
        tvRepository = new TvRepository(apiService, dao);
    }

    public void fetchMovieDetails(Long movieId)
    {
        tvRepository.getTvMovieDetails(movieId)
                .doOnSubscribe(disposable -> addToDisposable(disposable))
                .subscribe(data -> getTvDetailsLiveData().postValue(data));
    }

    public MutableLiveData<Resource<TvEntity>> getTvDetailsLiveData()
    {
        return tvDetailsLiveData;
    }
}
