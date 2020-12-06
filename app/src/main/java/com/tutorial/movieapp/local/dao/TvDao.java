package com.tutorial.movieapp.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.tutorial.movieapp.local.entity.TvEntity;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface TvDao extends GenericDao<TvEntity>
{
    @Query("SELECT * FROM tventity WHERE id= :id")
    TvEntity getTvById(Long id);

    @Query("SELECT * FROM tventity WHERE id= :id")
    Flowable<TvEntity> getTvDetailsById(Long id);

    @Query("SELECT * FROM tventity WHERE page= :page")
    List<TvEntity> getTvsByPage(Long page);
}
