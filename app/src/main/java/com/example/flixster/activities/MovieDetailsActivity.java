package com.example.flixster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.flixster.R;
import com.example.flixster.databinding.ActivityMovieDetailsBinding;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String TAG = "MovieDetailsActivity";
    private ActivityMovieDetailsBinding binding;
    Movie movie;

    ImageView ivBackdrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        binding.setMovie(movie);
        Log.d(TAG, String.format("Showing details for '%s'", movie.getTitle()));

        Float voteAverage = movie.getVoteAverage() / 2;
        binding.rbVoteAverage.setRating(voteAverage);
        Log.d(TAG, String.valueOf(voteAverage));

        if (movie.getVoteAverage() >= 8) {
            openVideo();
        }

        binding.ivBackdrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "videoid" + movie.getVideoId());
                openVideo();
            }
        });
    }

    protected void openVideo() {
        if (movie.getVideoId() != null) {
            Intent intent = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
            intent.putExtra("videoId", movie.getVideoId());
            MovieDetailsActivity.this.startActivity(intent);
        }
    }
}
