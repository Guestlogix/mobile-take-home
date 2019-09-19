package com.guestlogix.takehome.network;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.net.ssl.HttpsURLConnection;

public class NetworkTask extends AsyncTask<UrlRequest, Integer, Void> {

    private static final int TIMEOUT = 5000;
    private NetworkResponseListener callback;
    private ConnectivityManager connectivityManager;

    NetworkTask(NetworkResponseListener callback, ConnectivityManager connectivityManager) {
        this.callback = callback;
        this.connectivityManager = connectivityManager;
    }

    /**
     * Cancel background network operation if we do not have network connectivity.
     */
    @Override
    protected void onPreExecute() {
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnected() ||
                    (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                            && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
                callback.onFailure("no network");
                cancel(true);
            }
        }
    }

    /**
     * Defines work to perform on the background thread.
     */
    @Override
    protected Void doInBackground(UrlRequest... requests) {
        if (!isCancelled() && requests != null && requests.length > 0) {
            UrlRequest request = requests[0];
            HttpsURLConnection connection = null;

            try {
                connection = (HttpsURLConnection) request.getURL().openConnection();
                // Timeout for reading InputStream arbitrarily set to 3000ms.
                connection.setReadTimeout(TIMEOUT);
                // Timeout for connection.connect() arbitrarily set to 3000ms.
                connection.setConnectTimeout(TIMEOUT);
                // For this use case, set HTTP method to GET.
                connection.setRequestMethod(request.getMethod());
                // Already true by default but setting just in case; needs to be true since this request
                // is carrying an input (response) body.
                connection.setDoInput(true);
                // Open communications link (network traffic occurs here).
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode != HttpsURLConnection.HTTP_OK) {
                    callback.onFailure("HTTP error code: " + responseCode);
                }

                // Retrieve the response body as an InputStream.
                InputStream stream = connection.getInputStream();

                try {
                    callback.onResponse(new JsonReader(new InputStreamReader(stream)));
                } catch (Exception e) {
                    callback.onFailure("error parsing : " + e.getMessage());
                }

                stream.close();
            } catch (IOException e) {

                String errorMessage = null;

                if (connection != null) {
                    errorMessage = getStringFromInputStream(connection.getErrorStream());
                }

                callback.onFailure(errorMessage);
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        return null;
    }

    /**
     * Updates the DownloadCallback with the result.
     */
    @Override
    protected void onPostExecute(Void result) {

    }

    /**
     * Override to add special behavior for cancelled AsyncTask.
     */
    @Override
    protected void onCancelled(Void result) {
    }

    private String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            if (is == null) {
                return "No stream";
            }

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }
}
