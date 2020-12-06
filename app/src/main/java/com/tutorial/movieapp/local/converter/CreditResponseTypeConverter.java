package com.tutorial.movieapp.local.converter;


import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tutorial.movieapp.remote.model.CreditResponse;

import java.lang.reflect.Type;

public class CreditResponseTypeConverter {

    @TypeConverter
    public CreditResponse fromString(String value) {
        Type listType = new TypeToken<CreditResponse>() {}.getType();
        CreditResponse creditResponse = new Gson().fromJson(value, listType);
        return creditResponse;
    }

    @TypeConverter
    public String fromList(CreditResponse creditResponse) {
        return new Gson().toJson(creditResponse);
    }
}
