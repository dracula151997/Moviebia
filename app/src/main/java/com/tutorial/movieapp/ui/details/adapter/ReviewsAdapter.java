package com.tutorial.movieapp.ui.details.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tutorial.movieapp.databinding.ListItemReviewsBinding;
import com.tutorial.movieapp.remote.model.Review;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder>
{
    private final List<Review> reviews;
    private final Context context;

    public ReviewsAdapter(Context context, List<Review> reviews)
    {
        this.reviews = reviews;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        ListItemReviewsBinding binding = ListItemReviewsBinding.inflate(inflater, parent, false);
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
        return reviews.size();
    }

    public Review getItem(int position)
    {
        return reviews.get(position);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder
    {
        private final ListItemReviewsBinding binding;

        public ViewHolder(@NonNull ListItemReviewsBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(Review review)
        {
            binding.name.setText(review.getAuthor());
            binding.reviewDesc.setText(review.getContent());
        }
    }
}
