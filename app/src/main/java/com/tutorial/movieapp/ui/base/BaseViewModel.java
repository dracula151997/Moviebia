package com.tutorial.movieapp.ui.base;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseViewModel extends ViewModel
{
    private static final String TAG = "BaseViewModel";
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected void addToDisposable(Disposable disposable)
    {
        compositeDisposable.remove(disposable);
        compositeDisposable.add(disposable);

    }

    public void onStop()
    {
        compositeDisposable.clear();
    }
}
