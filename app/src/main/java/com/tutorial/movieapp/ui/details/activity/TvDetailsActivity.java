package com.tutorial.movieapp.ui.details.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.squareup.picasso.Picasso;
import com.tutorial.movieapp.R;
import com.tutorial.movieapp.databinding.ActivityMovieDetailsBinding;
import com.tutorial.movieapp.di.factory.ViewModelFactory;
import com.tutorial.movieapp.interfaces.MovieDetailsListener;
import com.tutorial.movieapp.local.entity.TvEntity;
import com.tutorial.movieapp.remote.model.Cast;
import com.tutorial.movieapp.remote.model.Crew;
import com.tutorial.movieapp.remote.model.Review;
import com.tutorial.movieapp.ui.base.BaseActivity;
import com.tutorial.movieapp.ui.details.adapter.CreditAdapter;
import com.tutorial.movieapp.ui.details.adapter.ReviewsAdapter;
import com.tutorial.movieapp.ui.details.adapter.SimilarTvAdapter;
import com.tutorial.movieapp.ui.details.viewmodel.TvDetailsViewModel;
import com.tutorial.movieapp.ui.main.adapters.RecyclerItemClickListener;
import com.tutorial.movieapp.utils.AppUtils;
import com.tutorial.movieapp.utils.NavigationUtil;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class TvDetailsActivity extends BaseActivity implements MovieDetailsListener
{

    @Inject
    ViewModelFactory factory;
    TvDetailsViewModel viewModel;
    private ActivityMovieDetailsBinding binding;
    private TvEntity tvEntity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);

        initViews();
        initViewModel();

    }

    private void initViewModel()
    {
        viewModel = new ViewModelProvider(this, factory).get(TvDetailsViewModel.class);

        viewModel.fetchMovieDetails(tvEntity.getId());

        subscribeObservers();
    }

    private void initViews()
    {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
        binding.setListener(this);

        tvEntity = getMovieFromIntent();
        Picasso.get().load(tvEntity.getPosterPath())
                .placeholder(R.drawable.placeholder_movie_img)
                .into(binding.movieImage);
        ViewCompat.setTransitionName(binding.movieImage, TRANSITION_IMAGE_NAME);

    }

    private TvEntity getMovieFromIntent()
    {
        if (getIntent() != null)
        {
            tvEntity = getIntent().getParcelableExtra(INTENT_MOVIE);
        }
        return tvEntity;
    }

    private void subscribeObservers()
    {
        viewModel.getTvDetailsLiveData().observe(this, resource ->
        {
            switch (resource.status)
            {
                case ERROR:
                    binding.loader.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    if (resource.data != null)
                    {
                        updateTvDetailUI(resource.data);
                    }
                    break;
            }
        });
    }

    private void updateTvDetailUI(TvEntity entity)
    {
        binding.loader.setVisibility(View.GONE);
        binding.movieTitle.setText(entity.getHeader());
        binding.movieDescription.setText(entity.getDescription());
        binding.genersCollection.setItems(AppUtils.getGenres(entity.getGenres()));
        binding.movieStatus.setItems(Collections.singletonList(entity.getStatus()));
        binding.movieDuration.setText(String.format("Session %s", entity.getNumberOfSeasons()));
        binding.readMoreTxt.setVisibility(View.VISIBLE);
        binding.reviewsTxt.setVisibility(View.VISIBLE);
        binding.similarMoviesTxt.setVisibility(View.VISIBLE);
        updateMovieCast(entity.getCasts());
        updateMovieCrew(entity.getCrews());
        updateSimilarMovies(entity.getSimilarTvEntities());
        updateMovieReviews(entity.getReviews());
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


    private void updateSimilarMovies(List<TvEntity> similarMovies)
    {
        SimilarTvAdapter adapter = new SimilarTvAdapter(this, similarMovies);
        binding.similarMoviesRecycler.setAdapter(adapter);

        binding.similarMoviesRecycler.addOnItemTouchListener(new RecyclerItemClickListener(this, (parentView, childView, position) ->
        {
            TvEntity movie = adapter.getItem(position);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                    new Pair<>(childView, TRANSITION_IMAGE_NAME));
            NavigationUtil.redirectToTvDetailsScreen(this, movie, options);
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
