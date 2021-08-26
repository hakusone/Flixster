package com.example.flixster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

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
        if (this.movies.get(position).getRating() >= 8) {
            return POPULAR;
        }
        return REGULAR;
    }

    // inflates a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == POPULAR) {
            View v2 = inflater.inflate(R.layout.item_movie2, parent, false);
            viewHolder = new ViewHolder(v2);
        }
        else {
            View v1 = inflater.inflate(R.layout.item_movie1, parent, false);
            viewHolder = new ViewHolder(v1);
        }

        return viewHolder;
    }

    // populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get the movie at the position
        Movie movie = movies.get(position);

        // bind movie data into the view holder
        holder.bind(movie);
    }

    // return total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;

            // if phone in landscape, set image to backdrop
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
                || movie.getRating() >= 8) {
                imageUrl = movie.getBackdropPath();
            }
            else {
                // else if portrait, set image to poster
                imageUrl = movie.getPosterPath();
            }

            Glide.with(context).load(imageUrl).placeholder(R.drawable.ic_baseline_movie_24).into(ivPoster);
        }
    }
}
