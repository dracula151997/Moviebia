package com.tutorial.movieapp.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.SnapHelper;

import com.tutorial.movieapp.AppConstants;
import com.tutorial.movieapp.R;
import com.tutorial.movieapp.databinding.FragmentMoviesBinding;
import com.tutorial.movieapp.di.factory.ViewModelFactory;
import com.tutorial.movieapp.local.entity.MovieEntity;
import com.tutorial.movieapp.ui.base.BaseFragment;
import com.tutorial.movieapp.ui.main.activity.MainActivity;
import com.tutorial.movieapp.ui.main.adapters.MoviesListAdapter;
import com.tutorial.movieapp.ui.main.adapters.PagerSnapHelper;
import com.tutorial.movieapp.ui.main.adapters.RecyclerItemClickListener;
import com.tutorial.movieapp.ui.main.adapters.RecyclerViewPaginator;
import com.tutorial.movieapp.ui.main.viewmodel.MovieListViewModel;
import com.tutorial.movieapp.utils.NavigationUtil;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class MoviesFragment extends BaseFragment implements AppConstants, RecyclerItemClickListener.OnRecyclerViewItemClickListener
{
    private static final String TAG = "MoviesFragment";

    @Inject
    ViewModelFactory factory;

    MovieListViewModel movieListViewModel;

    private FragmentMoviesBinding binding;

    private MoviesListAdapter movieAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
        initializeViewModel();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        binding = FragmentMoviesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initializeAdapter();
        attachSnapHelperToRecycler();
        handleRecyclerPagination();
    }

    private void initializeAdapter()
    {
        movieAdapter = new MoviesListAdapter(activity);
        binding.movieListRecycler.setAdapter(movieAdapter);
        binding.movieListRecycler.addOnItemTouchListener(new RecyclerItemClickListener(context, this));
    }

    private void attachSnapHelperToRecycler()
    {
        SnapHelper snapHelper = new PagerSnapHelper(position ->
        {
            MovieEntity movie = movieAdapter.getItem(position);
            ((MainActivity) activity).updateBackground(movie.getPosterPath());
        });

        snapHelper.attachToRecyclerView(binding.movieListRecycler);
    }

    private void handleRecyclerPagination()
    {
        binding.movieListRecycler.addOnScrollListener(new RecyclerViewPaginator(binding.movieListRecycler)
        {
            @Override
            public boolean isLastPage()
            {
                return movieListViewModel.isLastPage();
            }

            @Override
            public void loadMore(Long page)
            {
                movieListViewModel.fetchMovies(page);
            }

            @Override
            public void loadFirstData(Long page)
            {
                movieListViewModel.fetchMovies(page);
            }
        });
    }

    private void initializeViewModel()
    {
        movieListViewModel = new ViewModelProvider(this, factory).get(MovieListViewModel.class);
        movieListViewModel.setType(MENU_MOVIE_ITEM.get(getArguments() == null ? 0 : getArguments().getInt(INTENT_CATEGORY)));

        movieListViewModel.getMoviesLiveData().observe(this, resource ->
        {
            switch (resource.status)
            {
                case LOADING:
                    displayLoader();
                    break;
                case SUCCESS:
                    hideLoader();
                    updateMovieList(resource.data);
                    break;
                case ERROR:
                    handleErrorResponse(resource.message);
                    break;
            }
        });


    }

    public void displayLoader()
    {
        binding.movieListRecycler.setVisibility(View.GONE);
        binding.loaderLayout.loaderContainer.setVisibility(View.VISIBLE);
        binding.loaderLayout.loader.start();
        ((MainActivity) requireActivity()).hideToolbar();
    }

    public void hideLoader()
    {
        binding.movieListRecycler.setVisibility(View.VISIBLE);
        binding.loaderLayout.loaderContainer.setVisibility(View.GONE);
        binding.loaderLayout.loader.stop();
        ((MainActivity) requireActivity()).displayToolbar();
    }

    private void handleErrorResponse(String message)
    {
        binding.loaderLayout.loader.setVisibility(View.GONE);
        binding.movieListRecycler.setVisibility(View.GONE);
        binding.emptyView.emptyContainer.setVisibility(View.VISIBLE);
        binding.emptyView.favDisplayText.setText(message);
        ((MainActivity) requireActivity()).clearBackground();
    }

    private void updateMovieList(List<MovieEntity> data)
    {
        binding.emptyView.emptyContainer.setVisibility(View.GONE);
        binding.movieListRecycler.setVisibility(View.VISIBLE);
        movieAdapter.setMovies(data);
        ((MainActivity) activity).updateBackground(data.get(0).getPosterPath());

    }

    @Override
    public void onDestroyView()
    {
        ((MainActivity) activity).clearBackground();
        super.onDestroyView();
    }

    @Override
    public void onItemClicked(View parentView, View childView, int position)
    {
        movieListViewModel.onStop();
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                new Pair<>(childView.findViewById(R.id.movie_image), TRANSITION_IMAGE_NAME));
        NavigationUtil.redirectToMovieDetailScreen(activity, movieAdapter.getItem(position), options);
    }
}