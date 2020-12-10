package com.tutorial.movieapp.ui.details.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tutorial.movieapp.R;
import com.tutorial.movieapp.databinding.ListItemSimilarMoviesBinding;
import com.tutorial.movieapp.local.entity.TvEntity;

import java.util.List;

public class SimilarTvAdapter extends RecyclerView.Adapter<SimilarTvAdapter.ViewHolder>
{
    private Context context;
    private List<TvEntity> similarVideos;

    public SimilarTvAdapter(Context context, List<TvEntity> similarVideos)
    {
        this.context = context;
        this.similarVideos = similarVideos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        ListItemSimilarMoviesBinding binding = ListItemSimilarMoviesBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.bindTo(getItem(position));
    }

    @Override
    public int getItemCount()
    {
        return similarVideos.size();
    }

    public TvEntity getItem(int position)
    {
        return similarVideos.get(position);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder
    {
        private final ListItemSimilarMoviesBinding binding;

        public ViewHolder(@NonNull ListItemSimilarMoviesBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(TvEntity entity)
        {
            Picasso.get()
                    .load(entity.getPosterPath())
                    .placeholder(R.drawable.placeholder_movie_img)
                    .into(binding.similarMovieImg);
        }
    }
}
