package com.tutorial.movieapp.local.converter;


import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tutorial.movieapp.local.entity.MovieEntity;

import java.lang.reflect.Type;
import java.util.List;

public class MovieListTypeConverter
{

    @TypeConverter
    public List<MovieEntity> fromString(String value)
    {
        Type listType = new TypeToken<List<MovieEntity>>()
        {
        }.getType();
        List<MovieEntity> movieEntities = new Gson().fromJson(value, listType);
        return movieEntities;
    }

    @TypeConverter
    public String fromList(List<MovieEntity> movieEntities)
    {
        return new Gson().toJson(movieEntities);
    }
}
