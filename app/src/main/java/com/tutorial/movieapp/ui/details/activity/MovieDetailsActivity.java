package com.tutorial.movieapp.ui.details.activity;

import android.os.Bundle;
import android.view.View;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.squareup.picasso.Picasso;
import com.tutorial.movieapp.R;
import com.tutorial.movieapp.databinding.ActivityMovieDetailsBinding;
import com.tutorial.movieapp.di.factory.ViewModelFactory;
import com.tutorial.movieapp.interfaces.MovieDetailsListener;
import com.tutorial.movieapp.local.entity.MovieEntity;
import com.tutorial.movieapp.remote.Resource;
import com.tutorial.movieapp.remote.model.Cast;
import com.tutorial.movieapp.remote.model.Crew;
import com.tutorial.movieapp.remote.model.Review;
import com.tutorial.movieapp.remote.model.Video;
import com.tutorial.movieapp.ui.base.BaseActivity;
import com.tutorial.movieapp.ui.details.adapter.CreditAdapter;
import com.tutorial.movieapp.ui.details.adapter.ReviewsAdapter;
import com.tutorial.movieapp.ui.details.adapter.SimilarMoviesAdapter;
import com.tutorial.movieapp.ui.details.adapter.VideoListAdapter;
import com.tutorial.movieapp.ui.details.viewmodel.MovieDetailsViewModel;
import com.tutorial.movieapp.ui.main.adapters.RecyclerItemClickListener;
import com.tutorial.movieapp.utils.AppUtils;
import com.tutorial.movieapp.utils.NavigationUtil;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MovieDetailsActivity extends BaseActivity implements MovieDetailsListener
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
        binding.setListener(this);

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
        binding.readMoreTxt.setVisibility(View.VISIBLE);
        binding.reviewsTxt.setVisibility(View.VISIBLE);
        binding.similarMoviesTxt.setVisibility(View.VISIBLE);
        updateVideosList(entity.getVideos());
        updateMovieCast(entity.getCasts());
        updateMovieCrew(entity.getCrews());
        updateSimilarMovies(entity.getSimilarMovies());
        updateMovieReviews(entity.getReviews());


    }

    private void updateVideosList(List<Video> videos)
    {
        VideoListAdapter adapter = new VideoListAdapter(this, videos);
        binding.movieVideosRecycler.smoothScrollToPosition(1);
        binding.movieVideosRecycler.setAdapter(adapter);

        binding.movieVideosRecycler.addOnItemTouchListener(new RecyclerItemClickListener(this, (parentView, childView, position) ->
        {
            NavigationUtil.redirectToVideoScreen(this, adapter.getItem(position).getKey());
        }));
    }

    private void updateMovieReviews(List<Review> reviews)
    {
        if (reviews.isEmpty())
        {
            binding.reviewsRecyclerView.setVisibility(View.GONE);
            binding.emptyViewTxt.setVisibility(View.VISIBLE);
        }
        ReviewsAdapter adapter = new ReviewsAdapter(this, reviews);
        binding.reviewsRecyclerView.setAdapter(adapter);

        binding.reviewsRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }


    private void updateSimilarMovies(List<MovieEntity> similarMovies)
    {
        SimilarMoviesAdapter adapter = new SimilarMoviesAdapter(this, similarMovies);
        binding.similarMoviesRecycler.setAdapter(adapter);

        binding.similarMoviesRecycler.addOnItemTouchListener(new RecyclerItemClickListener(this, (parentView, childView, position) ->
        {
            MovieEntity movie = adapter.getItem(position);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                    new Pair<>(childView, TRANSITION_IMAGE_NAME));
            NavigationUtil.redirectToMovieDetailScreen(this, movie, options);
        }));
    }

    private void updateMovieCrew(List<Crew> crews)
    {
        CreditAdapter adapter = new CreditAdapter(this, CREDIT_CREW, crews);
        binding.includedLayout.crewRecyclerView.setAdapter(adapter);
    }

    private void updateMovieCast(List<Cast> casts)
    {
        CreditAdapter adapter = new CreditAdapter(this, casts);
        binding.includedLayout.castRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onReadMoreClicked()
    {
        if (binding.includedLayout.expandableLayout.isExpanded())
        {
            binding.readMoreTxt.setText(getString(R.string.read_more));
            binding.includedLayout.expandableLayout.collapse();
        } else
        {
            binding.readMoreTxt.setText(getString(R.string.read_less));
            binding.includedLayout.expandableLayout.expand();
        }
    }
}