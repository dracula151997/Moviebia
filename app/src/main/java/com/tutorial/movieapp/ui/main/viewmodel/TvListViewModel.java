package com.tutorial.movieapp.ui.main.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.tutorial.movieapp.local.dao.TvDao;
import com.tutorial.movieapp.local.entity.TvEntity;
import com.tutorial.movieapp.remote.Resource;
import com.tutorial.movieapp.remote.TvApiService;
import com.tutorial.movieapp.repository.TvRepository;
import com.tutorial.movieapp.ui.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

public class TvListViewModel extends BaseViewModel
{
    private static final String TAG = "TvListViewModel";
    private TvRepository tvRepository;
    private String type;

    private MutableLiveData<Resource<List<TvEntity>>> tvListLiveData = new MutableLiveData<>();

    @Inject
    public TvListViewModel(TvDao tvDao, TvApiService tvApiService)
    {
        tvRepository = new TvRepository(tvApiService, tvDao);
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void fetchTvList(Long page)
    {
        tvRepository.getTvListByType(page, type)
                .doOnSubscribe(disposable -> {
                    addToDisposable(disposable);
                    Log.d(TAG, "fetchTvList: disposable is added");
                })
                .subscribe(resource -> {
                    Log.d(TAG, "fetchTvList: Subscribed");
                    getTvListLiveData().postValue(resource);
                });
    }

    public boolean isLastPage()
    {
        return (tvListLiveData != null && !tvListLiveData.getValue().data.isEmpty()) && tvListLiveData.getValue().data.get(0).isLastPage();
    }

    public MutableLiveData<Resource<List<TvEntity>>> getTvListLiveData()
    {
        return tvListLiveData;
    }
}
