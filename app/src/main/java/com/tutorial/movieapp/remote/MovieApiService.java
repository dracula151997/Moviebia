package com.tutorial.movieapp.remote;

import com.tutorial.movieapp.local.entity.MovieEntity;
import com.tutorial.movieapp.remote.model.CreditResponse;
import com.tutorial.movieapp.remote.model.MovieApiResponse;
import com.tutorial.movieapp.remote.model.ReviewApiResponse;
import com.tutorial.movieapp.remote.model.VideoResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiService
{
    @GET("movie/{type}?language=en-US&region=US")
    Observable<MovieApiResponse> fetchMoviesByType(@Path("type") String type,
                                                   @Query("page") long page);


    @GET("/3/movie/{movieId}")
    Observable<MovieEntity> fetchMovieDetail(@Path("movieId") String movieId);


    @GET("/3/movie/{movieId}/videos")
    Observable<VideoResponse> fetchMovieVideo(@Path("movieId") String movieId);

    @GET("/3/movie/{movieId}/credits")
    Observable<CreditResponse> fetchCastDetail(@Path("movieId") String movieId);


    @GET("/3/movie/{movieId}/similar")
    Observable<MovieApiResponse> fetchSimilarMovie(@Path("movieId") String movieId,
                                                   @Query("page") long page);


    @GET("/3/movie/{movieId}/reviews")
    Observable<ReviewApiResponse> fetchMovieReviews(@Path("movieId") String movieId);


    @GET("/3/search/movie")
    Observable<MovieApiResponse> searchMoviesByQuery(@Query("query") String query,
                                                     @Query("page") String page);
}
