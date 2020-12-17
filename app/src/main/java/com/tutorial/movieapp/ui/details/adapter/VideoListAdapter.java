package com.tutorial.movieapp.ui.details.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.tutorial.movieapp.AppConstants;
import com.tutorial.movieapp.R;
import com.tutorial.movieapp.databinding.ListItemMovieVideoBinding;
import com.tutorial.movieapp.remote.model.Video;

import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder>
{
    private ListItemMovieVideoBinding binding;
    private Context context;
    private List<Video> videos;


    public VideoListAdapter(Context context, List<Video> videos)
    {
        this.context = context;
        this.videos = videos;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = ListItemMovieVideoBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {

        holder.binding.youtubePlayerView.initialize(AppConstants.YOUTUBE_API_KEY, new YouTubeThumbnailView.OnInitializedListener()
        {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader)
            {
                Video video = getItem(position);
                youTubeThumbnailLoader.setVideo(video.getKey());
                youTubeThumbnailView.setImageBitmap(null);

                youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener()
                {
                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s)
                    {
                        youTubeThumbnailView.setVisibility(View.VISIBLE);
                        holder.binding.btnPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                        holder.binding.videoContainer.setVisibility(View.VISIBLE);
                        youTubeThumbnailLoader.release();
                    }

                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason)
                    {

                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult)
            {

            }
        });
//        holder.bindTo(getItem(position));
    }

    @Override
    public int getItemCount()
    {
        return videos.size();
    }

    public Video getItem(int position)
    {
        return videos.get(position);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder
    {
        private final ListItemMovieVideoBinding binding;
        public ViewHolder(@NonNull ListItemMovieVideoBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;

        }

     /*   public void bindTo(Video video)
        {
            binding.youtubePlayerView.initialize(AppConstants.YOUTUBE_API_KEY, new YouTubeThumbnailView.OnInitializedListener()
            {
                @Override
                public void onInitializationSuccess(YouTubeThumbnailView thumbnailView, YouTubeThumbnailLoader thumbnailLoader)
                {
                    thumbnailLoader.setVideo(video.getKey());
                    thumbnailView.setImageBitmap(null);

                    thumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener()
                    {
                        @Override
                        public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s)
                        {
                            youTubeThumbnailView.setVisibility(View.VISIBLE);
                            binding.playVideoBtn.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                            thumbnailLoader.release();
                        }

                        @Override
                        public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason)
                        {

                        }
                    });

                }

                @Override
                public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult)
                {

                }
            });
        }*/
    }
}
