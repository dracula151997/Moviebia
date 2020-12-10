package com.tutorial.movieapp.ui.main.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.tutorial.movieapp.R;
import com.tutorial.movieapp.databinding.MainActivityBinding;
import com.tutorial.movieapp.ui.base.BaseActivity;
import com.tutorial.movieapp.ui.custom.menu.MenuDrawerToggle;
import com.tutorial.movieapp.ui.search.activity.MovieSearchActivity;
import com.tutorial.movieapp.ui.search.activity.TvSearchActivity;
import com.tutorial.movieapp.utils.AppUtils;
import com.tutorial.movieapp.utils.NavigationUtil;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends BaseActivity implements HasSupportFragmentInjector
{
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    private MainActivityBinding mainBinding;
    private MenuDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initViews();
    }

    private void initViews()
    {
        setToolbar();
        initDrawer();
        mainBinding.toolbarLayout.radioGroup.setOnCheckedChangeListener(this::onCheckedChanged);
        mainBinding.toolbarLayout.search.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                handleSearchIconClicked(view);
            }
        });

    }

    private void initDrawer()
    {
        drawerToggle = new MenuDrawerToggle(this,
                mainBinding.drawerLayout,
                mainBinding.toolbarLayout.toolbar,
                mainBinding.leftDrawer,
                R.string.open, R.string.close,
                AppUtils.getMenuList(this))
        {
            @Override
            public void onSwitch(int selectedPosition, int topPosition)
            {
                onCheckedChanged(mainBinding.toolbarLayout.radioGroup, mainBinding.toolbarLayout.radioGroup.getCheckedRadioButtonId());

            }
        };

        drawerToggle.syncState();
        mainBinding.drawerLayout.addDrawerListener(drawerToggle);

    }

    private void setToolbar()
    {
        setSupportActionBar(mainBinding.toolbarLayout.toolbar);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void displayToolbar()
    {
        mainBinding.toolbarLayout.toolbar.setVisibility(View.VISIBLE);
    }

    public void hideToolbar()
    {
        mainBinding.toolbarLayout.toolbar.setVisibility(View.INVISIBLE);
    }

    public void updateBackground(String url)
    {
        mainBinding.backgroundSwitcher.updateCurrentBackground(url);
    }

    public void clearBackground()
    {
        mainBinding.backgroundSwitcher.clearImage();
    }

    public void handleSearchIconClicked(View view)
    {
        switch (mainBinding.toolbarLayout.radioGroup.getCheckedRadioButtonId())
        {
            case R.id.movie_switch:
                //TODO redirect to movie search screen
                NavigationUtil.redirectToSearchScreen(this, MovieSearchActivity.class);
                break;
            case R.id.tv_switch:
                //TODO redirect to tv search screen
                NavigationUtil.redirectToSearchScreen(this, TvSearchActivity.class);
                break;
        }
    }

    public void onCheckedChanged(RadioGroup radioGroup, int selectedPosition)
    {
        switch (selectedPosition)
        {
            case R.id.movie_switch:
                //TODO navigate to movie fragment
                NavigationUtil.replaceFragment(this, R.id.moviesFragment, drawerToggle.getSelectedPosition());
                break;

            case R.id.tv_switch:
                //TODO navigate to TV fragment
                NavigationUtil.replaceFragment(this, R.id.tvFragment, drawerToggle.getSelectedPosition());
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        drawerToggle.onDestroy();
    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector()
    {
        return dispatchingAndroidInjector;
    }
}