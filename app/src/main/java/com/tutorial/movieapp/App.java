package com.tutorial.movieapp;

import android.app.Activity;
import android.app.Application;

import com.tutorial.movieapp.di.components.DaggerAppComponent;
import com.tutorial.movieapp.di.modules.ApiModule;
import com.tutorial.movieapp.di.modules.DBModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class App extends Application implements HasActivityInjector
{
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;


    @Override
    public AndroidInjector<Activity> activityInjector()
    {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        DaggerAppComponent.builder()
                .application(this)
                .apiModule(new ApiModule())
                .dbModule(new DBModule())
                .build()
                .inject(this);
    }
}
