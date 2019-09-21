package com.guestlogix.takehome.network.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.guestlogix.takehome.network.ErrorCode;
import com.guestlogix.takehome.network.GuestlogixException;
import com.guestlogix.takehome.network.ResponseListener;

import java.io.BufferedInputStream;
import java.io.InputStream;

public class DownloadTask extends Task {

    private ResponseListener<Bitmap> callback;
    private ConnectivityManager connectivityManager;
    private int width, height;

    public DownloadTask(
        int width,
        int height,
        ConnectivityManager connectivityManager,
        ResponseListener<Bitmap> callback
    ) {
        this.width = width;
        this.height = height;
        this.callback = callback;
        this.connectivityManager = connectivityManager;
    }

    @Override
    protected void onResponse(InputStream stream) {
        try {
            callback.onResponse(decodeImageStream(stream));
        } catch (Exception e) {
            callback.onFailure(new GuestlogixException(e.getMessage(), ErrorCode.IMAGE_DOWNLOAD_ERROR));
        }
    }

    @Override
    protected void onError(GuestlogixException exception) {
        callback.onFailure(exception);
    }

    @Override
    protected NetworkInfo getActiveNetworkInfo() {
        return connectivityManager.getActiveNetworkInfo();
    }

    private Bitmap decodeImageStream(InputStream stream) throws Exception {

        BufferedInputStream bufferedInputStream = new BufferedInputStream(stream);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        if (bufferedInputStream.available() > 0) {
            bufferedInputStream.mark(bufferedInputStream.available());
            BitmapFactory.decodeStream(bufferedInputStream, null, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, width, height);
            bufferedInputStream.reset();
        }

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(bufferedInputStream, null, options);
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight
                && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
