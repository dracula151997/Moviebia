package com.tutorial.movieapp.local.converter;


import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tutorial.movieapp.remote.model.Crew;

import java.lang.reflect.Type;
import java.util.List;

public class CrewListTypeConverter {

    @TypeConverter
    public List<Crew> fromString(String value) {
        Type listType = new TypeToken<List<Crew>>() {}.getType();
        List<Crew> casts = new Gson().fromJson(value, listType);
        return casts;
    }

    @TypeConverter
    public String fromList(List<Crew> casts) {
        return new Gson().toJson(casts);
    }
}
