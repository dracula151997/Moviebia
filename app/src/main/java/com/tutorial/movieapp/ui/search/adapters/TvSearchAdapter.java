package com.tutorial.movieapp.ui.search.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tutorial.movieapp.R;
import com.tutorial.movieapp.databinding.MovieListItemBinding;
import com.tutorial.movieapp.local.entity.TvEntity;

import java.util.List;

public class TvSearchAdapter extends RecyclerView.Adapter<TvSearchAdapter.ViewHolder>
{
    private final Context context;
    private final List<TvEntity> movies;

    public TvSearchAdapter(Context context, List<TvEntity> movies)
    {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        MovieListItemBinding binding = MovieListItemBinding.inflate(inflater, parent, false);
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
        return movies.size();
    }

    public TvEntity getItem(int position)
    {
        return movies.get(position);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder
    {
        private final MovieListItemBinding binding;

        public ViewHolder(@NonNull MovieListItemBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(TvEntity entity)
        {
            Picasso.get()
                    .load(entity.getPosterPath())
                    .placeholder(R.drawable.placeholder_movie_img)
                    .into(binding.movieImage);
        }
    }
}
