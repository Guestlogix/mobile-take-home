package com.guestlogix.takehome.network.imageloader;

import android.graphics.Bitmap;
import android.util.LruCache;
import androidx.annotation.NonNull;

public class ImageCache {

    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    final int cacheSize = maxMemory / 8;
    private LruCache<String, Bitmap> bitmapCache;

    public ImageCache() {
        bitmapCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            bitmapCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return bitmapCache.get(key);
    }

    public boolean clear() {
        bitmapCache.evictAll();
        return true;
    }
}
