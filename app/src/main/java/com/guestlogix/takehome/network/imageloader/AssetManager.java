package com.guestlogix.takehome.network.imageloader;

import android.graphics.Bitmap;
import android.util.SparseArray;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.guestlogix.takehome.R;
import com.guestlogix.takehome.Utils.Optional;

import java.lang.ref.WeakReference;

public class AssetManager {

    private static final AssetManager localInstance = new AssetManager();
    private ImageLoader imageLoader;
    private SparseArray<WeakReference<DownloadTask>> tasksMap;

    private AssetManager() {
        ImageCache imageCache = new ImageCache();
        imageLoader = new ImageLoader(imageCache);
        tasksMap = new SparseArray<>();
    }

    public static AssetManager getInstance() {
        return localInstance;
    }

    public void loadImage(String url, @NonNull ImageView imageView) {

        if(Optional.ofNullable(imageView.getTag()).map(tag -> !tag.equals(url)).orElse(true)) {
            imageView.setImageBitmap(null);
            imageView.setImageResource(R.drawable.place_holder);
            loadImage(url, imageView, new ImageLoader.ImageLoaderCallback() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                    imageView.setTag(url);
                }

                @Override
                public void onError(String message) {

                }
            });
        }
    }

    public void loadImage(String url, @NonNull ImageView imageView, ImageLoader.ImageLoaderCallback callback) {
        String key = url + 'X' + imageView.getWidth() + 'X' + imageView.getHeight();

        WeakReference<DownloadTask> weakTasks = tasksMap.get(key.hashCode());
        if (null != weakTasks) {
            DownloadTask previousTask = weakTasks.get();
            if (null != previousTask) {
                previousTask.cancel(true);
            }
        }

        DownloadTask imageLoadingTask = imageLoader.loadImage(url, key, imageView, callback);
        tasksMap.put(key.hashCode(), new WeakReference<>(imageLoadingTask));
    }
}

