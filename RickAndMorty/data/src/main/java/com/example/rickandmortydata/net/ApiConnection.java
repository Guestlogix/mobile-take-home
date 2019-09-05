package com.example.rickandmortydata.net;

import android.support.v4.util.Preconditions;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiConnection implements Callable<String> {

    private URL mUrl;
    private String mResponse;
    private byte[] mByteResponse;

    public ApiConnection(String url) throws MalformedURLException {
        mUrl = new URL(url);
    }

    /**
     * Calls the URL synchronously.
     * However, this should be executed in a background thread.
     * @return A string response
     */
    String requestSyncCall() {
        connectToApi();
        return mResponse;
    }

    byte[] requestImageResource() {
        connectToApi();
        return mByteResponse;
    }

    private void connectToApi() {
        OkHttpClient okHttpClient = createClient();
        final Request request = new Request.Builder()
                .url(mUrl)
                .build();
        try {
            final Response response = okHttpClient.newCall(request).execute();
            if (isJsonResponse(response)) {
                mResponse = response.body().string();
            } else if (isImageResponse(response)) {
                mByteResponse = response.body().bytes();
            }
        } catch (IOException e) {
        }

    }

    private boolean isJsonResponse(Response response) {
        return response.header("content-type").contains("json");
    }

    private boolean isImageResponse(Response response) {
        return response.header("content-type").contains("image");
    }

    private OkHttpClient createClient() {
        final OkHttpClient client = new OkHttpClient();
        return client;
    }

    @Override
    public String call() throws Exception {
        return null;
    }
}
