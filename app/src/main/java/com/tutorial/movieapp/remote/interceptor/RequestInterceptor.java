package com.tutorial.movieapp.remote.interceptor;

import com.tutorial.movieapp.AppConstants;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestInterceptor implements Interceptor
{
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException
    {
        //TODO get request from chain
        Request originalRequest = chain.request();
        //TODO get url from request
        HttpUrl originalUrl = originalRequest.url();
        //TODO add API_KEY as params in url
        HttpUrl url = originalUrl.newBuilder().addQueryParameter("api_key", AppConstants.TMDB_API_KEY).build();
        //TODO create new builder for request and add the new url with params
        Request newRequest = originalRequest.newBuilder().url(url).build();
        //TODO process request
        return chain.proceed(newRequest);
    }
}
