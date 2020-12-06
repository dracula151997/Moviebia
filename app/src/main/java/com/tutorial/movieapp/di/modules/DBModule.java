package com.tutorial.movieapp.di.modules;

import android.app.Application;

import androidx.room.Room;

import com.tutorial.movieapp.local.AppDatabase;
import com.tutorial.movieapp.local.dao.MovieDao;
import com.tutorial.movieapp.local.dao.TvDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DBModule
{
    @Singleton
    @Provides
    AppDatabase provideDatabase(Application application)
    {
        return Room.databaseBuilder(application, AppDatabase.class, "movies_app.db")
                .allowMainThreadQueries().build();
    }

    @Provides
    @Singleton
    MovieDao provideMovieDao(AppDatabase database)
    {
        return database.movieDao();
    }

    @Provides
    @Singleton
    TvDao provideTvDao(AppDatabase database)
    {
        return database.tvDao();
    }
}
