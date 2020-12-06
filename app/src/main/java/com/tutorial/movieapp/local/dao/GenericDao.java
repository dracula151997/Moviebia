package com.tutorial.movieapp.local.dao;

import androidx.room.Insert;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

public interface GenericDao<T>
{
    @Insert(onConflict = REPLACE)
    long[] insertAll(List<T> items);

    @Insert(onConflict = REPLACE)
    long insert(T item);

    @Update(onConflict = REPLACE)
    int update(T item);
}
