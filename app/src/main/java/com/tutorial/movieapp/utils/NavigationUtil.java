package com.tutorial.movieapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.tutorial.movieapp.AppConstants;
import com.tutorial.movieapp.R;
import com.tutorial.movieapp.local.entity.MovieEntity;
import com.tutorial.movieapp.local.entity.TvEntity;
import com.tutorial.movieapp.ui.details.activity.MovieDetailsActivity;
import com.tutorial.movieapp.ui.details.activity.TvDetailsActivity;
import com.tutorial.movieapp.ui.details.activity.VideoActivity;

public class NavigationUtil implements AppConstants
{
    public static void redirectToSearchScreen(Activity activity, Class<?> clazz)
    {
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
    }

    public static void redirectToMovieDetailScreen(Activity activity, MovieEntity entity, ActivityOptionsCompat options)
    {
        Intent intent = new Intent(activity, MovieDetailsActivity.class);
        intent.putExtra(INTENT_MOVIE, entity);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    public static void redirectToVideoScreen(Context context,
                                             String videoKey) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra(INTENT_VIDEO_KEY, videoKey);
        context.startActivity(intent);
    }

    public static void redirectToTvDetailsScreen(Activity activity, TvEntity entity, ActivityOptionsCompat options)
    {
        Intent intent = new Intent(activity, TvDetailsActivity.class);
        intent.putExtra(INTENT_MOVIE, entity);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    public static void replaceFragment(Activity activity, int navId, int selectedPosition)
    {
        Bundle bundle = new Bundle();
        bundle.putInt(INTENT_CATEGORY, selectedPosition);
        Navigation.findNavController(activity, R.id.fragment_nav_host)
                .navigate(navId, bundle, new NavOptions.Builder()
                        .setLaunchSingleTop(true)
                        .setEnterAnim(R.animator.flip_right_in)
                        .setExitAnim(R.animator.flip_right_out)
                        .setPopEnterAnim(R.animator.flip_left_in)
                        .setPopExitAnim(R.animator.flip_left_out)
                        .build());
    }
}
