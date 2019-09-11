package com.phayaotown.travel.utils;

import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class ImageLoader {

    public static void loadImageUrl(String url, AppCompatImageView imageView) {
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(getRequestOptions())
                .load(url)
                .into(imageView);
    }

    private static RequestOptions getRequestOptions() {
        return new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(android.R.color.darker_gray)
                .error(android.R.color.darker_gray);
    }
}