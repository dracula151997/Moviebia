package com.tutorial.movieapp.di.modules;

import com.tutorial.movieapp.ui.main.fragment.MoviesFragment;
import com.tutorial.movieapp.ui.main.fragment.TvFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityFragmentModule
{
    @ContributesAndroidInjector
    abstract MoviesFragment contributeMoviesFragment();

    @ContributesAndroidInjector
    abstract TvFragment contributeTvFragment();
}
