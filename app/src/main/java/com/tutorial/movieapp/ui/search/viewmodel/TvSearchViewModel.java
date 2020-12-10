package com.tutorial.movieapp.ui.search.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.tutorial.movieapp.local.dao.TvDao;
import com.tutorial.movieapp.local.entity.TvEntity;
import com.tutorial.movieapp.remote.Resource;
import com.tutorial.movieapp.remote.TvApiService;
import com.tutorial.movieapp.repository.TvRepository;
import com.tutorial.movieapp.ui.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

public class TvSearchViewModel extends BaseViewModel
{

    private TvRepository tvRepository;

    private MutableLiveData<Resource<List<TvEntity>>> tvMoviesLiveData = new MutableLiveData<>();

    @Inject
    public TvSearchViewModel(TvApiService apiService, TvDao tvDao)
    {
        tvRepository = new TvRepository(apiService, tvDao);
    }

    public void searchForMovie(String query)
    {
        tvRepository.searchForMovie(query, 1L)
                .doOnSubscribe(disposable -> addToDisposable(disposable))
                .subscribe(resource -> getTvMoviesLiveData().postValue(resource));
    }

    public MutableLiveData<Resource<List<TvEntity>>> getTvMoviesLiveData()
    {
        return tvMoviesLiveData;
    }
}
