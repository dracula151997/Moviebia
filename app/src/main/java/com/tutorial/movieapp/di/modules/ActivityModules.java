package com.tutorial.movieapp.di.modules;

import com.tutorial.movieapp.ui.main.activity.MainActivity;
import com.tutorial.movieapp.ui.search.MovieSearchActivity;
import com.tutorial.movieapp.ui.search.TvSearchActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModules
{
    @ContributesAndroidInjector(modules = ActivityFragmentModule.class)
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector
    abstract TvSearchActivity contributeTvSearchActivity();

    @ContributesAndroidInjector
    abstract MovieSearchActivity contributeMovieSearchActivity();
}
