package com.tutorial.movieapp.di.modules;

import com.tutorial.movieapp.ui.details.activity.MovieDetailsActivity;
import com.tutorial.movieapp.ui.details.activity.TvDetailsActivity;
import com.tutorial.movieapp.ui.main.activity.MainActivity;
import com.tutorial.movieapp.ui.search.activity.MovieSearchActivity;
import com.tutorial.movieapp.ui.search.activity.TvSearchActivity;

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

    @ContributesAndroidInjector
    abstract MovieDetailsActivity contributeMovieDetailsActivity();

    @ContributesAndroidInjector
    abstract TvDetailsActivity contributeTvDetailsActivity();
}
