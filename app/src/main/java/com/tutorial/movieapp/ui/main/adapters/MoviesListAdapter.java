package com.tutorial.movieapp.ui.main.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tutorial.movieapp.R;
import com.tutorial.movieapp.databinding.MovieListItemBinding;
import com.tutorial.movieapp.local.entity.MovieEntity;
import com.tutorial.movieapp.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.ViewHolder>
{
    private final List<MovieEntity> movies;
    private final Activity activity;

    public MoviesListAdapter(Activity activity)
    {
        this.activity = activity;
        this.movies = new ArrayList<>();
    }

    public void setMovies(List<MovieEntity> movies)
    {
        int startPosition = this.movies.size();
        this.movies.addAll(movies);
        notifyItemRangeChanged(startPosition, movies.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        MovieListItemBinding binding = MovieListItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.bindTo(movies.get(position));
    }

    @Override
    public int getItemCount()
    {
        return movies.size();
    }

    public MovieEntity getItem(int position)
    {
        return movies.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private final MovieListItemBinding binding;

        public ViewHolder(@NonNull MovieListItemBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;

            int width = AppUtils.getScreenWidth(activity);

            itemView.setLayoutParams(new RecyclerView.LayoutParams(Float.valueOf(width * 0.85f).intValue(),
                    RecyclerView.LayoutParams.WRAP_CONTENT));
        }

        public void bindTo(MovieEntity entity)
        {
            Picasso.get()
                    .load(entity.getPosterPath())
                    .placeholder(R.drawable.placeholder_movie_img)
                    .into(binding.movieImage);

        }
    }
}
