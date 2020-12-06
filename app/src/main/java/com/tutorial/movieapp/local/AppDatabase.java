package com.tutorial.movieapp.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.tutorial.movieapp.local.converter.CastListTypeConverter;
import com.tutorial.movieapp.local.converter.CreditResponseTypeConverter;
import com.tutorial.movieapp.local.converter.CrewListTypeConverter;
import com.tutorial.movieapp.local.converter.GenreListTypeConverter;
import com.tutorial.movieapp.local.converter.ReviewListTypeConverter;
import com.tutorial.movieapp.local.converter.StringListConverter;
import com.tutorial.movieapp.local.converter.TvListTypeConverter;
import com.tutorial.movieapp.local.converter.VideoListTypeConverter;
import com.tutorial.movieapp.local.dao.MovieDao;
import com.tutorial.movieapp.local.dao.TvDao;
import com.tutorial.movieapp.local.entity.MovieEntity;
import com.tutorial.movieapp.local.entity.TvEntity;

@Database(entities = {MovieEntity.class, TvEntity.class}, version = 1)
@TypeConverters({CastListTypeConverter.class, CreditResponseTypeConverter.class,
        CrewListTypeConverter.class, GenreListTypeConverter.class,
        ReviewListTypeConverter.class, StringListConverter.class,
        TvListTypeConverter.class, VideoListTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase
{
    public abstract MovieDao movieDao();

    public abstract TvDao tvDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context)
    {
        if (INSTANCE == null)
        {
            synchronized (AppDatabase.class)
            {
                if (INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "movie_app.db")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
