package com.guestlogix.takehome.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class GuestlogixApi {

    private static final String EPISODES_LIST = "episode";
    private static final String CHARACTERS_LIST = "character";

    private static final int TIMEOUT = 5000;

    private static GuestlogixApi API;

    private ConnectivityManager connectivityManager;

    public GuestlogixApi(Context context) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

    }

    public static void initialize(Context context) {
        if (API == null) {
            API = new GuestlogixApi(context);
        }
    }

    public static GuestlogixApi getAPI() {
        return API;
    }

    public void getEpisodesList(Integer page, NetworkResponseListener listener) {
        UrlRequest.UrlRequestBuilder req = new UrlRequest.UrlRequestBuilder()
                            .method(Method.GET)
                            .apiPath(EPISODES_LIST);

        if(page != null) {
            req.queryParams(getPagingParam(page));
        }

        execute(req.build(), listener);
    }

    public void getCharactersList(Integer page, List<String> ids, NetworkResponseListener listener) {
        UrlRequest.UrlRequestBuilder req = new UrlRequest.UrlRequestBuilder()
            .method(Method.GET)
            .apiPath(CHARACTERS_LIST);

        if(ids != null) {
            req.pathParams(ids);
        }

        execute(req.build(), listener);
    }

    private Map<String, String> getPagingParam(int page) {
        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(page));
        return params;
    }

    private void execute(UrlRequest request, NetworkResponseListener listener) {
        HttpsURLConnection connection = null;
        String result = null;
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
                listener.onFailure("HTTP error code: " + responseCode);
                return;
            }

            // Retrieve the response body as an InputStream.
            InputStream stream = connection.getInputStream();

            try {
                listener.onResponse(new JsonReader(new InputStreamReader(stream)));
            } catch (Exception e) {
                listener.onFailure("error parsing : " + e.getMessage());
            }

            stream.close();
        } catch (IOException e) {

            String errorMessage = null;

            if (connection != null) {
                errorMessage = getStringFromInputStream(connection.getErrorStream());
            }

            listener.onFailure(errorMessage);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
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
