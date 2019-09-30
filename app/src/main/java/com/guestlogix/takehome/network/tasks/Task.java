package com.guestlogix.takehome.network.tasks;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.guestlogix.takehome.network.Request;
import com.guestlogix.takehome.network.ErrorCode;
import com.guestlogix.takehome.network.GuestlogixException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;

abstract class Task extends AsyncTask<Request, Integer, Void> {

    private static final int TIMEOUT = 5000;

    /**
     * Cancel background network operation if we do not have network connectivity.
     */
    @Override
    protected void onPreExecute() {
        NetworkInfo networkInfo = getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() ||
            (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            // If no connectivity, cancel task and update Callback with null data.
            onError(new GuestlogixException("Network Error", ErrorCode.NETWORK));
            cancel(true);
        }
    }

    /**
     * Defines work to perform on the background thread.
     */
    @Override
    protected Void doInBackground(Request... requests) {
        if (!isCancelled() && requests != null && requests.length > 0) {
            Request urlReq = requests[0];
            try {
                InputStream is = getResponseStream(urlReq);
                onResponse(is);
            } catch (Exception e) {
                onError(new GuestlogixException(e.getMessage(), ErrorCode.UNKNOWN));
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    private InputStream getResponseStream(Request req) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) req.getURL().openConnection();
        // Timeout for reading InputStream arbitrarily set to 3000ms.
        connection.setReadTimeout(TIMEOUT);
        // Timeout for connection.connect() arbitrarily set to 3000ms.
        connection.setConnectTimeout(TIMEOUT);

        connection.setRequestMethod(req.getMethod());

        connection.setDoInput(true);

        connection.setDoOutput(false);

        connection.connect();
        int responseCode = connection.getResponseCode();
        if (responseCode != HttpsURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code: " + responseCode);
        }
            // Retrieve the response body as an InputStream.
        return connection.getInputStream();
    }

    abstract protected void onResponse(InputStream is);
    abstract protected void onError(GuestlogixException exception);

    abstract protected NetworkInfo getActiveNetworkInfo();
}