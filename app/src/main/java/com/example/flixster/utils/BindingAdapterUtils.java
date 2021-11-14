package com.example.flixster.utils;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixster.R;

public class BindingAdapterUtils {
    @BindingAdapter({"bind:backdropImageUrl"})
    public static void loadBackdropImage(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                .placeholder(R.drawable.backdrop_placeholder)
                .fitCenter()
                .transform(new CenterInside(), new RoundedCorners(20))
                .into(view);
    }

    @BindingAdapter({"bind:posterImageUrl"})
    public static void loadPosterImage(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                .placeholder(R.drawable.poster_placeholder)
                .fitCenter()
                .transform(new CenterInside(), new RoundedCorners(20))
                .into(view);
    }

    @BindingAdapter({"bind:preview"})
    public static void loadPreview(ImageView view, String url) {
        if (url != null) {
            view.setVisibility(View.VISIBLE);
        }
        else {
            view.setVisibility(View.INVISIBLE);
        }
    }
}
