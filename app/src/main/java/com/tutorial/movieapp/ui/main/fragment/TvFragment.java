package com.tutorial.movieapp.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.SnapHelper;

import com.tutorial.movieapp.AppConstants;
import com.tutorial.movieapp.databinding.FragmentMoviesBinding;
import com.tutorial.movieapp.di.factory.ViewModelFactory;
import com.tutorial.movieapp.local.entity.TvEntity;
import com.tutorial.movieapp.ui.base.BaseFragment;
import com.tutorial.movieapp.ui.main.activity.MainActivity;
import com.tutorial.movieapp.ui.main.adapters.PagerSnapHelper;
import com.tutorial.movieapp.ui.main.adapters.RecyclerItemClickListener;
import com.tutorial.movieapp.ui.main.adapters.RecyclerViewPaginator;
import com.tutorial.movieapp.ui.main.adapters.TvListAdapter;
import com.tutorial.movieapp.ui.main.viewmodel.TvListViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class TvFragment extends BaseFragment implements AppConstants, RecyclerItemClickListener.OnRecyclerViewItemClickListener
{
    private static final String TAG = "TvFragment";
    private FragmentMoviesBinding binding;

    @Inject
    ViewModelFactory viewModelFactory;

    TvListViewModel tvListViewModel;

    private TvListAdapter adapter;


    public TvFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
        initViewModel();
        subscribeObservers();
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
        initRecyclerAdapter();
        attachSnapHelperForRecycler();
        handleRecyclerViewPagination();

    }

    private void subscribeObservers()
    {
        tvListViewModel.getTvListLiveData().observe(this, resource ->
        {
            switch (resource.status)
            {
                case LOADING:
                    showLoader();
                    break;
                case SUCCESS:
                    hideLoader();
                    updateRecycler(resource.data);
                    break;
                case ERROR:
                    handleErrorResponse(resource.message);
                    break;
            }

        });
    }

    private void handleErrorResponse(String message)
    {
        binding.emptyView.emptyContainer.setVisibility(View.VISIBLE);
        binding.movieListRecycler.setVisibility(View.GONE);
        binding.emptyView.favDisplayText.setText(message);
        ((MainActivity) activity).clearBackground();

    }

    private void updateRecycler(List<TvEntity> data)
    {
        binding.emptyView.emptyContainer.setVisibility(View.GONE);
        adapter.addItems(data);
        ((MainActivity) activity).updateBackground(data.get(0).getPosterPath());
    }

    private void hideLoader()
    {
        binding.movieListRecycler.setVisibility(View.VISIBLE);
        binding.loaderLayout.loader.setVisibility(View.GONE);
        binding.loaderLayout.loader.stop();
        ((MainActivity) activity).displayToolbar();
    }

    private void handleRecyclerViewPagination()
    {
        binding.movieListRecycler.addOnScrollListener(new RecyclerViewPaginator(binding.movieListRecycler)
        {
            @Override
            public boolean isLastPage()
            {
                return tvListViewModel.isLastPage();
            }

            @Override
            public void loadMore(Long page)
            {
                tvListViewModel.fetchTvList(page);
            }

            @Override
            public void loadFirstData(Long page)
            {
                tvListViewModel.fetchTvList(page);
            }
        });
    }

    private void showLoader()
    {
        binding.movieListRecycler.setVisibility(View.GONE);
        binding.loaderLayout.loader.setVisibility(View.VISIBLE);
        binding.loaderLayout.loader.start();
        ((MainActivity) activity).hideToolbar();
    }

    private void initRecyclerAdapter()
    {
        adapter = new TvListAdapter(requireActivity());
        binding.movieListRecycler.setAdapter(adapter);
        binding.movieListRecycler.addOnItemTouchListener(new RecyclerItemClickListener(requireContext(), this));
    }

    private void attachSnapHelperForRecycler()
    {
        SnapHelper snapHelper = new PagerSnapHelper(position ->
        {
            TvEntity item = adapter.getItem(position);
            ((MainActivity) requireActivity()).updateBackground(item.getPosterPath());
        });

        snapHelper.attachToRecyclerView(binding.movieListRecycler);
    }

    private void initViewModel()
    {
        tvListViewModel = ViewModelProviders.of(this, viewModelFactory).get(TvListViewModel.class);
        tvListViewModel.setType(MENU_TV_ITEM.get(getArguments() == null ? 0 : getArguments().getInt(INTENT_CATEGORY)));
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

    }
}