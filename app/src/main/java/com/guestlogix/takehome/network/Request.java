package com.guestlogix.takehome.network;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * Simple Network Request
 */
public class Request {

    private static final String BASE_URL = "https://rickandmortyapi.com/api/";

    private Method method;
    private String url;

    private Request(@NonNull Method method, @NonNull String url) {
        this.method = method;
        this.url = url;
    }

    public String getMethod() {
        return this.method.name();
    }

    public URL getURL() throws MalformedURLException {
        return new URL(this.url);
    }

    static class UrlRequestBuilder {

        private Method method = null;
        private String apiPath = null;
        private String pathParams;
        private Map<String, String> queryParams;

        public UrlRequestBuilder method(Method method){
            this.method = method;
            return this;
        }

        public UrlRequestBuilder apiPath(String apiPath){
            this.apiPath = apiPath;
            return this;
        }

        public UrlRequestBuilder pathParams(String pathParams) {
            this.pathParams = pathParams;
            return this;
        }

        public UrlRequestBuilder queryParams(Map<String, String> queryParams) {
            this.queryParams = queryParams;
            return this;
        }

        public Request build() {
            StringBuilder sb = new StringBuilder(BASE_URL);

            if(apiPath != null)
                sb.append(apiPath);

            if(!TextUtils.isEmpty(pathParams)) {
                sb.append("/");
                sb.append(pathParams);
            }

            if(queryParams != null && !queryParams.isEmpty()) {
                sb.append("?");

                Iterator<Map.Entry<String, String>> iterator = queryParams.entrySet().iterator();

                while (iterator.hasNext()) {
                    Map.Entry<String, String> param = iterator.next();

                    sb.append(param.getKey());
                    sb.append("=");
                    sb.append(param.getValue());

                    if (iterator.hasNext())
                        sb.append("&");
                }
            }

            return new Request(method, sb.toString());
        }
    }

    public static class ImageRequestBuilder {
        private Method method = Method.GET;
        private String url = null;

        public ImageRequestBuilder method(Method method){
            this.method = method;
            return this;
        }

        public ImageRequestBuilder url(String url){
            this.url = url;
            return this;
        }

        public Request build() {
            return new Request(method, url);
        }
    }
}
