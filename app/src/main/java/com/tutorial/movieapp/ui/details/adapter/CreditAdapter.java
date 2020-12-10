package com.tutorial.movieapp.ui.details.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tutorial.movieapp.AppConstants;
import com.tutorial.movieapp.R;
import com.tutorial.movieapp.databinding.ListItemCastBinding;
import com.tutorial.movieapp.remote.model.Cast;
import com.tutorial.movieapp.remote.model.Crew;

import java.util.Collections;
import java.util.List;

public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.ViewHolder>
{
    private final List<Cast> casts;
    private final List<Crew> crews;
    private final String creditType;
    private final Context context;

    public CreditAdapter(Context context, String creditType)
    {
        this.creditType = creditType;
        this.context = context;
        this.casts = Collections.emptyList();
        this.crews = Collections.emptyList();
    }

    public CreditAdapter(Context context, List<Cast> casts)
    {
        this.creditType = AppConstants.CREDIT_CAST;
        this.context = context;
        this.casts = casts;
        this.crews = Collections.emptyList();
    }

    public CreditAdapter(Context context, String type, List<Crew> crews)
    {
        this.creditType = type;
        this.context = context;
        this.crews = crews;
        this.casts = Collections.emptyList();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        ListItemCastBinding binding = ListItemCastBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        if (isCast())
        {
            Cast cast = casts.get(position);
            holder.bindCast(cast);
        } else
        {
            Crew crew = crews.get(position);
            holder.bindCrew(crew);
        }

    }

    @Override
    public int getItemCount()
    {
        if (isCast()) return casts.size();
        return crews.size();
    }

    public boolean isCast()
    {
        return creditType.equalsIgnoreCase(AppConstants.CREDIT_CAST);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private final ListItemCastBinding binding;

        public ViewHolder(@NonNull ListItemCastBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindCast(Cast cast)
        {
            Picasso.get()
                    .load(String.format(AppConstants.IMAGE_URL, cast.getProfilePath()))
                    .placeholder(R.drawable.image_placeholder)
                    .into(binding.profileImage);

            binding.creditName.setText(cast.getName());
            binding.creditInfo.setText(cast.getCharacter());
        }

        public void bindCrew(Crew crew)
        {
            Picasso.get()
                    .load(String.format(AppConstants.IMAGE_URL, crew.getProfilePath()))
                    .placeholder(R.drawable.image_placeholder)
                    .into(binding.profileImage);

            binding.creditName.setText(crew.getName());
            binding.creditInfo.setText(crew.getJob());
        }
    }
}
