package com.tutorial.movieapp.local.converter;


import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tutorial.movieapp.local.entity.TvEntity;

import java.lang.reflect.Type;
import java.util.List;

public class TvListTypeConverter
{

    @TypeConverter
    public List<TvEntity> fromString(String value)
    {
        Type listType = new TypeToken<List<TvEntity>>()
        {
        }.getType();
        List<TvEntity> tvEntities = new Gson().fromJson(value, listType);
        return tvEntities;
    }

    @TypeConverter
    public String fromList(List<TvEntity> movieEntities)
    {
        return new Gson().toJson(movieEntities);
    }
}
