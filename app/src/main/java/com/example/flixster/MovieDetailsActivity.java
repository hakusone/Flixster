package com.example.flixster;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.flixster.databinding.ActivityMovieDetailsBinding;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;
import org.w3c.dom.Text;

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
            openVideo(true);
        }

        binding.ivBackdrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "videoid" + movie.getVideoId());
                if (movie.getVoteAverage() >= 8) {
                    openVideo(true);
                }
                else {
                    openVideo(false);
                }
            }
        });
    }

    protected void openVideo(boolean instantPlay) {
        if (movie.getVideoId() != null) {
            Intent intent = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
            intent.putExtra("videoId", movie.getVideoId());
            intent.putExtra("instantPlay", instantPlay);
            MovieDetailsActivity.this.startActivity(intent);
        }
    }
}
