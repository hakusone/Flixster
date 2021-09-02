package com.example.flixster.adapters;

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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.MovieDetailsActivity;
import com.example.flixster.R;
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
            View v2 = inflater.inflate(R.layout.item_movie2, parent, false);
            viewHolder = new ViewHolder2(v2);
        }
        else {
            View v1 = inflater.inflate(R.layout.item_movie1, parent, false);
            viewHolder = new ViewHolder1(v1);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == POPULAR) {
            ViewHolder2 vh2 = (ViewHolder2) holder;
            configureViewHolder2(vh2, position);
        }
        else {
            ViewHolder1 vh1 = (ViewHolder1) holder;
            configureViewHolder1(vh1, position);
        }
    }

    private void configureViewHolder1(ViewHolder1 vh1, int position) {
        Movie movie = movies.get(position);
        if (movie != null) {
            vh1.getTvTitle().setText(movie.getTitle());
            vh1.getTvOverview().setText(movie.getOverview());
            String imageUrl;

            // if phone in landscape, set image to backdrop
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
                    || movie.getVoteAverage() >= 8) {
                imageUrl = movie.getBackdropPath();
            }
            else {
                imageUrl = movie.getPosterPath();
            }

            Log.d(TAG, imageUrl);

            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_baseline_movie_24)
                    .error(R.drawable.ic_baseline_movie_24)
                    .into(vh1.getIvPoster());
        }
    }

    private void configureViewHolder2(ViewHolder2 vh2, int position) {
        Movie movie = movies.get(position);
        String imageUrl = movie.getBackdropPath();

        Log.d(TAG, imageUrl);

        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_baseline_movie_24)
                .error(R.drawable.ic_baseline_movie_24)
                .into(vh2.getIvPoster());
    }

    // return total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTitle;
        private TextView tvOverview;
        private ImageView ivPoster;

        public TextView getTvTitle() {
            return tvTitle;
        }

        public void setTvTitle(TextView tvTitle) {
            this.tvTitle = tvTitle;
        }

        public TextView getTvOverview() {
            return tvOverview;
        }

        public void setTvOverview(TextView tvOverview) {
            this.tvOverview = tvOverview;
        }

        public ImageView getIvPoster() {
            return ivPoster;
        }

        public void setIvPoster(ImageView ivPoster) {
            this.ivPoster = ivPoster;
        }

        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getBindingAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Movie movie = movies.get(position);
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                context.startActivity(intent);
            }
        }
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivPoster;

        public ImageView getIvPoster() {
            return ivPoster;
        }

        public void setIvPoster(ImageView ivPoster) {
            this.ivPoster = ivPoster;
        }

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
            int position = getBindingAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Movie movie = movies.get(position);
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                context.startActivity(intent);
            }
        }
    }
}
