package com.tutorial.movieapp.ui.details.activity;

import android.os.Bundle;
import android.view.View;

import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;
import com.tutorial.movieapp.R;
import com.tutorial.movieapp.databinding.ActivityMovieDetailsBinding;
import com.tutorial.movieapp.di.factory.ViewModelFactory;
import com.tutorial.movieapp.local.entity.MovieEntity;
import com.tutorial.movieapp.remote.Resource;
import com.tutorial.movieapp.ui.base.BaseActivity;
import com.tutorial.movieapp.ui.details.viewmodel.MovieDetailsViewModel;
import com.tutorial.movieapp.utils.AppUtils;

import java.util.Collections;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MovieDetailsActivity extends BaseActivity
{
    private static final String TAG = "MovieDetailsActivity";
    ActivityMovieDetailsBinding binding;

    @Inject
    ViewModelFactory viewModelFactory;

    private MovieDetailsViewModel viewModel;

    private MovieEntity movie;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        movie = getMovieFromIntent();
        initViewModel();
        initViews();
        subscribeObservers();
    }

    private void initViews()
    {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);

        Picasso.get().load(movie.getPosterPath()).into(binding.movieImage);
        ViewCompat.setTransitionName(binding.movieImage, TRANSITION_IMAGE_NAME);
    }

    private MovieEntity getMovieFromIntent()
    {
        if (getIntent() != null)
        {
            movie = getIntent().getParcelableExtra(INTENT_MOVIE);
        }
        return movie;
    }

    private void initViewModel()
    {
        viewModel = new ViewModelProvider(this, viewModelFactory).get(MovieDetailsViewModel.class);
        viewModel.fetchMovieDetailsById(movie.getId());

    }

    private void subscribeObservers()
    {
        viewModel.getMovieDetailLiveData().observe(this, new Observer<Resource<MovieEntity>>()
        {
            @Override
            public void onChanged(Resource<MovieEntity> resource)
            {
                switch (resource.status)
                {
                    case SUCCESS:
                        if (resource.data != null)
                        {
                            updateMovieDetailsUI(resource.data);
                        }
                        break;
                    case ERROR:
                        binding.loader.setVisibility(View.GONE);
                        break;
                }
            }
        });
    }

    public void updateMovieDetailsUI(MovieEntity entity)
    {
        binding.loader.setVisibility(View.GONE);
        binding.movieTitle.setText(entity.getHeader());
        binding.movieDescription.setText(movie.getDescription());
        binding.genersCollection.setItems(AppUtils.getGenres(entity.getGenres()));
        binding.movieStatus.setItems(Collections.singletonList(entity.getStatus()));
        binding.movieDuration.setText(String.format("%s min", entity.getRuntime()));

    }
}