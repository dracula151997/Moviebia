package com.tutorial.movieapp.ui.search.activity;

import android.app.SearchManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.util.Pair;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.SnapHelper;

import com.tutorial.movieapp.R;
import com.tutorial.movieapp.databinding.ActivityMovieSearchBinding;
import com.tutorial.movieapp.di.factory.ViewModelFactory;
import com.tutorial.movieapp.local.entity.TvEntity;
import com.tutorial.movieapp.ui.base.BaseActivity;
import com.tutorial.movieapp.ui.main.adapters.PagerSnapHelper;
import com.tutorial.movieapp.ui.main.adapters.RecyclerItemClickListener;
import com.tutorial.movieapp.ui.search.adapters.TvSearchAdapter;
import com.tutorial.movieapp.ui.search.viewmodel.TvSearchViewModel;
import com.tutorial.movieapp.utils.AppUtils;
import com.tutorial.movieapp.utils.NavigationUtil;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class TvSearchActivity extends BaseActivity implements SearchView.OnQueryTextListener, RecyclerItemClickListener.OnRecyclerViewItemClickListener
{
    @Inject
    ViewModelFactory factory;
    private ActivityMovieSearchBinding binding;
    private TvSearchViewModel viewModel;

    private TvSearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);

        initViews();
        initViewModel();

        binding.includedLayout.loaderLayout.loaderContainer.setVisibility(View.GONE);
    }

    private void initViewModel()
    {
        viewModel = new ViewModelProvider(this, factory).get(TvSearchViewModel.class);

        subscribeObserver();

    }

    private void subscribeObserver()
    {
        viewModel.getTvMoviesLiveData().observe(this, resource ->
        {
            switch (resource.status)
            {
                case LOADING:
                    showLoader();
                    break;
                case SUCCESS:
                    hideLoader();
                    updateUI(resource.data);
                    break;
                case ERROR:
                    handleError(resource.message);
                    break;
            }
        });
    }

    private void handleError(String message)
    {
        binding.includedLayout.loaderLayout.loaderContainer.setVisibility(View.GONE);
        binding.includedLayout.movieListRecycler.setVisibility(View.GONE);
        binding.includedLayout.emptyView.emptyContainer.setVisibility(View.VISIBLE);
        binding.includedLayout.emptyView.favDisplayText.setText(message);
    }

    private void initViews()
    {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_search);

        setupSearchView();

    }

    private void updateUI(List<TvEntity> data)
    {
        adapter = new TvSearchAdapter(this, data);
        binding.includedLayout.movieListRecycler.setAdapter(adapter);
        updateBackground(adapter.getItem(0).getPosterPath());

        SnapHelper snapHelper = new PagerSnapHelper(position ->
        {
            TvEntity item = adapter.getItem(position);
            updateBackground(item.getPosterPath());
        });
        snapHelper.attachToRecyclerView(binding.includedLayout.movieListRecycler);

        binding.includedLayout.movieListRecycler.addOnItemTouchListener(new RecyclerItemClickListener(this, this));
    }

    private void updateBackground(String url)
    {
        binding.backgroundSwitcher.updateCurrentBackground(url);
    }

    private void setupSearchView()
    {
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        binding.searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        binding.searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        binding.searchView.setIconifiedByDefault(false);
        binding.searchView.setOnQueryTextListener(this);

        EditText searchEditText = binding.searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.white, getTheme()));
        searchEditText.setHintTextColor(getResources().getColor(R.color.white, getTheme()));
        Typeface typeface = ResourcesCompat.getFont(this, R.font.gt_medium);
        searchEditText.setTypeface(typeface);
    }

    private void showLoader()
    {
        binding.includedLayout.movieListRecycler.setVisibility(View.GONE);
        binding.includedLayout.emptyView.emptyContainer.setVisibility(View.GONE);
        binding.includedLayout.loaderLayout.loaderContainer.setVisibility(View.VISIBLE);
    }

    public void hideLoader()
    {
        binding.includedLayout.movieListRecycler.setVisibility(View.VISIBLE);
        binding.includedLayout.emptyView.emptyContainer.setVisibility(View.GONE);
        binding.includedLayout.loaderLayout.loaderContainer.setVisibility(View.GONE);
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        AppUtils.closeKeyboard(this);
        viewModel.searchForMovie(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
        return false;
    }

    @Override
    public void onItemClicked(View parentView, View childView, int position)
    {
        viewModel.onStop();
        TvEntity item = adapter.getItem(position);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                new Pair<>(childView.findViewById(R.id.movie_image), TRANSITION_IMAGE_NAME)
        );
        NavigationUtil.redirectToTvDetailsScreen(this, item, options);
    }
}