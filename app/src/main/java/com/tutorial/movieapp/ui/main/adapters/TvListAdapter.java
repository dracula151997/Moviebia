package com.tutorial.movieapp.ui.main.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tutorial.movieapp.R;
import com.tutorial.movieapp.databinding.MovieListItemBinding;
import com.tutorial.movieapp.local.entity.TvEntity;
import com.tutorial.movieapp.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class TvListAdapter extends RecyclerView.Adapter<TvListAdapter.ViewHolder>
{
    private final List<TvEntity> tvEntities;

    private final Activity activity;

    public TvListAdapter(Activity activity)
    {
        this.activity = activity;
        tvEntities = new ArrayList<>();
    }

    public void addItems(List<TvEntity> tvEntities)
    {
        int startPosition = this.tvEntities.size();
        this.tvEntities.addAll(tvEntities);
        notifyItemRangeChanged(startPosition, tvEntities.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        MovieListItemBinding movieBinding = MovieListItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(movieBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.bindTo(tvEntities.get(position));
    }

    @Override
    public int getItemCount()
    {
        return tvEntities.size();
    }

    public TvEntity getItem(int position)
    {
        return tvEntities.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private final MovieListItemBinding movieListItemBinding;

        public ViewHolder(@NonNull MovieListItemBinding movieListItemBinding)
        {
            super(movieListItemBinding.getRoot());
            this.movieListItemBinding = movieListItemBinding;

            int width = AppUtils.getScreenWidth(activity);

            itemView.setLayoutParams(new RecyclerView.LayoutParams(Float.valueOf(width * 0.85f).intValue(),
                    RecyclerView.LayoutParams.WRAP_CONTENT));
        }

        public void bindTo(TvEntity tvEntity)
        {
            Picasso.get()
                    .load(tvEntity.getPosterPath())
                    .placeholder(R.drawable.placeholder_movie_img)
                    .into(movieListItemBinding.movieImage);
        }
    }
}
