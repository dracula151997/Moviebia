package com.tutorial.movieapp.local.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.tutorial.movieapp.local.entity.MovieEntity;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface MovieDao extends GenericDao<MovieEntity>
{
    @Query("SELECT * FROM MovieEntity WHERE id= :movieId")
    MovieEntity getMovieById(Long movieId);

    @Query("SELECT * FROM MovieEntity WHERE id= :id")
    Flowable<MovieEntity> getMovieDetailsById(Long id);

    @Query("SELECT * FROM MovieEntity WHERE page= :page")
    List<MovieEntity> getMoviesByPage(Long page);
}
