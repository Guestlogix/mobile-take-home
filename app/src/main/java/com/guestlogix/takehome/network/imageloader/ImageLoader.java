package com.guestlogix.takehome.network.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;

import com.guestlogix.takehome.R;

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
            imageLoaderCallback.onBitmapLoaded(cachedBitmap);
        } else {
            DownloadTask task = new DownloadTask(iv.getWidth(), iv.getHeight(), new DownloadCallback<DownloadTask.Result>() {

                @Override
                public void updateFromDownload(DownloadTask.Result result) {
                    if(result.resultValue != null) {
                        imageLoaderCallback.onBitmapLoaded(result.resultValue);
                        imageCache.addBitmapToMemoryCache(key, result.resultValue);
                    } else {
                        imageLoaderCallback.onError(result.exception);
                    }
                }

                @Override
                public NetworkInfo getActiveNetworkInfo() {
                    ConnectivityManager connectivityManager =
                        (ConnectivityManager) iv.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    return connectivityManager.getActiveNetworkInfo();
                }
            });

            task.execute(url);

            return task;
        }
        return null;
    }

    public interface ImageLoaderCallback {
        void onBitmapLoaded(Bitmap bitmap);

        void onError(String message);
    }
}