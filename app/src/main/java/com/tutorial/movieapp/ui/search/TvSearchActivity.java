package com.tutorial.movieapp.ui.search;

import android.os.Bundle;

import com.tutorial.movieapp.AppConstants;
import com.tutorial.movieapp.R;
import com.tutorial.movieapp.ui.base.BaseActivity;

import dagger.android.AndroidInjection;

public class TvSearchActivity extends BaseActivity implements AppConstants
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_search);
        AndroidInjection.inject(this);
    }
}