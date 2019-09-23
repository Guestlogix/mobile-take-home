package com.guestlogix.takehome.binding;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;

import com.guestlogix.takehome.data.Episode;
import com.guestlogix.takehome.network.imageloader.AssetManager;

/**
 * Contains {@link BindingAdapter}s for the {@link Episode} list.
 */
public class ImageViewBindings {

    @BindingAdapter("android:url")
    public void loadImage(AppCompatImageView imageView, String url) {
        AssetManager.getInstance().loadImage(url, imageView);
    }
}