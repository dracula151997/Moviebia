package com.tutorial.movieapp.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.tutorial.movieapp.AppConstants;
import com.tutorial.movieapp.R;

public class NavigationUtil implements AppConstants
{
    public static void redirectToSearchScreen(Activity activity, Class<?> clazz)
    {
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
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
