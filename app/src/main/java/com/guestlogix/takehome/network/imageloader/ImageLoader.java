package com.guestlogix.takehome.network.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.widget.ImageView;

import com.guestlogix.takehome.R;
import com.guestlogix.takehome.network.GuestlogixException;
import com.guestlogix.takehome.network.Request;
import com.guestlogix.takehome.network.ResponseListener;
import com.guestlogix.takehome.network.tasks.DownloadTask;
import com.guestlogix.takehome.utils.Optional;

public class ImageLoader {

    private ImageCache imageCache;

    public ImageLoader(ImageCache imageCache) {
        this.imageCache = imageCache;
    }

    /**
     * @param url                 absolute url to the image to download.
     * @param imageLoaderCallback to get callback with the downloaded image bitmap.
     */
    public DownloadTask loadImage(String url, String key, ImageView iv, ImageLoaderCallback imageLoaderCallback) {

        if (url == null) {
            imageLoaderCallback.onError("invalid image url");
            return null;
        }

        //if image found in cache notify observer with bitmap, otherwise start image download
        Bitmap cachedBitmap = imageCache.getBitmapFromMemCache(key);
        if (null != cachedBitmap) {
            iv.setTag(url);
            imageLoaderCallback.onBitmapLoaded(cachedBitmap);
        } else {
            if(Optional.ofNullable(iv.getTag()).map(tag -> !tag.equals(url)).orElse(true)) {
                iv.setImageBitmap(null);
                iv.setImageResource(R.drawable.place_holder);
            }

            iv.setTag(url);

            DownloadTask task = new DownloadTask(
                iv.getWidth(),
                iv.getHeight(),
                (ConnectivityManager) iv.getContext().getSystemService(Context.CONNECTIVITY_SERVICE),
                new ResponseListener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap image) {
                        if(image != null) {
                            imageLoaderCallback.onBitmapLoaded(image);
                            imageCache.addBitmapToMemoryCache(key, image);
                        }

                    }

                    @Override
                    public void onFailure(GuestlogixException message) {

                    }
                });
            task.execute(new Request.ImageRequestBuilder().url(url).build());

            return task;
        }
        return null;
    }

    public interface ImageLoaderCallback {
        void onBitmapLoaded(Bitmap bitmap);

        void onError(String message);
    }
}