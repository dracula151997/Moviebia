package com.tutorial.movieapp.di.modules;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tutorial.movieapp.AppConstants;
import com.tutorial.movieapp.remote.MovieApiService;
import com.tutorial.movieapp.remote.TvApiService;
import com.tutorial.movieapp.remote.interceptor.NetworkInterceptor;
import com.tutorial.movieapp.remote.interceptor.RequestInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule
{
    @Provides
    @Singleton
    Gson provideGson()
    {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    Cache provideCache(Application application)
    {
        long cacheSize = 10 * 1024 * 1024; // 10 MB
        File httpCacheDirectory = new File(application.getCacheDir(), "http-cache");
        return new Cache(httpCacheDirectory, cacheSize);
    }

    @Provides
    @Singleton
    NetworkInterceptor provideNetworkInterceptor(Application application)
    {
        return new NetworkInterceptor(application.getApplicationContext());
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache, NetworkInterceptor networkInterceptor)
    {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(loggingInterceptor);
        httpClient.addInterceptor(networkInterceptor);
        httpClient.cache(cache);
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.addNetworkInterceptor(new RequestInterceptor());
        return httpClient.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient client)
    {
        return new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    MovieApiService provideMovieApiService(Retrofit retrofit)
    {
        return retrofit.create(MovieApiService.class);
    }

    @Provides
    @Singleton
    TvApiService provideTvApiService(Retrofit retrofit)
    {
        return retrofit.create(TvApiService.class);
    }
}
