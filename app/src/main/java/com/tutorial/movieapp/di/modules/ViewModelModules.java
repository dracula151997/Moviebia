package com.tutorial.movieapp.di.modules;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.tutorial.movieapp.di.ViewModelKey;
import com.tutorial.movieapp.di.factory.ViewModelFactory;
import com.tutorial.movieapp.ui.details.viewmodel.MovieDetailsViewModel;
import com.tutorial.movieapp.ui.main.viewmodel.MovieListViewModel;
import com.tutorial.movieapp.ui.main.viewmodel.TvListViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModules
{
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewModel.class)
    protected abstract ViewModel bindMovieListViewModel(MovieListViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TvListViewModel.class)
    protected abstract ViewModel bindTvListViewModel(TvListViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel.class)
    protected abstract ViewModel bindMovieDetailsViewModel(MovieDetailsViewModel viewModel);

}
