package com.example.flixster.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.flixster.MovieDetailsActivity;
import com.example.flixster.R;
import com.example.flixster.databinding.ItemMovie1Binding;
import com.example.flixster.databinding.ItemMovie2Binding;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = "MovieAdapter";

    Context context;
    List<Movie> movies;

    private final int REGULAR = 0, POPULAR = 1;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // returns the view type of the item at position for the purposes of view recycling.
    @Override
    public int getItemViewType(int position) {
        if (this.movies.get(position).getVoteAverage() >= 8) {
            return POPULAR;
        }
        return REGULAR;
    }

    // inflates a layout from XML and returning the holder
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == POPULAR) {
            ItemMovie2Binding item2Binding =
                    ItemMovie2Binding.inflate(inflater, parent, false);
            viewHolder = new ViewHolder2(item2Binding);
        }
        else {
            ItemMovie1Binding item1Binding =
                    ItemMovie1Binding.inflate(inflater, parent, false);
            viewHolder = new ViewHolder1(item1Binding);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Movie movie = movies.get(position);

        if (holder.getItemViewType() == POPULAR) {
            ViewHolder2 vh2 = (ViewHolder2) holder;
            vh2.binding.setMovie(movie);
            vh2.binding.executePendingBindings();
        }
        else {
            ViewHolder1 vh1 = (ViewHolder1) holder;
            vh1.binding.setMovie(movie);
            vh1.binding.executePendingBindings();
        }
    }

    // return total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ItemMovie1Binding binding;

        public ViewHolder1(ItemMovie1Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getBindingAdapterPosition();
            Log.d(TAG, "position: " + Integer.toString(position));
            if (position != RecyclerView.NO_POSITION) {
                Movie movie = movies.get(position);
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                context.startActivity(intent);
            }
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ItemMovie2Binding binding;

        public ViewHolder2(ItemMovie2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
            int position = getBindingAdapterPosition();
            Log.d(TAG, "position: " + Integer.toString(position));
            if (position != RecyclerView.NO_POSITION) {
                Movie movie = movies.get(position);
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                context.startActivity(intent);
            }
        }
    }
}
