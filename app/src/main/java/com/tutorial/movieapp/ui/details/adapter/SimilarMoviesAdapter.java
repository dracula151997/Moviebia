package com.tutorial.movieapp.ui.details.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tutorial.movieapp.databinding.ListItemSimilarMoviesBinding;
import com.tutorial.movieapp.local.entity.MovieEntity;

import java.util.List;

public class SimilarMoviesAdapter extends RecyclerView.Adapter<SimilarMoviesAdapter.ViewHolder>
{
    private final Context context;
    private final List<MovieEntity> similarMovies;

    public SimilarMoviesAdapter(Context context, List<MovieEntity> similarMovies)
    {
        this.context = context;
        this.similarMovies = similarMovies;
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
        return similarMovies.size();
    }

    public MovieEntity getItem(int position)
    {
        return similarMovies.get(position);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder
    {
        private final ListItemSimilarMoviesBinding binding;

        public ViewHolder(@NonNull ListItemSimilarMoviesBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(MovieEntity movie)
        {
            Picasso.get()
                    .load(movie.getPosterPath())
                    .into(binding.similarMovieImg);
        }
    }
}
