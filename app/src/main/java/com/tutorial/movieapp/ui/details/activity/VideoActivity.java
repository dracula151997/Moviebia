package com.tutorial.movieapp.ui.details.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.tutorial.movieapp.AppConstants;
import com.tutorial.movieapp.R;
import com.tutorial.movieapp.databinding.LayoutVideoPlayerBinding;

public class VideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, AppConstants
{
    private static final int RC_RECOVERY = 100;

    private String videoKey;

    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        if (getIntent() != null)
        {
            videoKey = getIntent().getStringExtra(INTENT_VIDEO_KEY);
        }

        initViews();
    }

    private void initViews()
    {
        LayoutVideoPlayerBinding binding = DataBindingUtil.setContentView(this, R.layout.layout_video_player);
        binding.playerView.initialize(YOUTUBE_API_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b)
    {
        youTubePlayer.setFullscreen(true);
        youTubePlayer.loadVideo(videoKey);

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult)
    {
        if (youTubeInitializationResult.isUserRecoverableError())
        {
            youTubeInitializationResult.getErrorDialog(this, RC_RECOVERY).show();
        } else
        {
            String error = String.format(getString(R.string.player_error), youTubeInitializationResult.toString());
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        }

    }
}
