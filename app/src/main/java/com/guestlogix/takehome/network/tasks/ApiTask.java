package com.guestlogix.takehome.network.tasks;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.JsonReader;

import com.guestlogix.takehome.network.ErrorCode;
import com.guestlogix.takehome.network.GuestlogixException;
import com.guestlogix.takehome.network.ResponseListener;
import com.guestlogix.takehome.network.response.ObjectMappingFactory;

import java.io.InputStream;
import java.io.InputStreamReader;

public class ApiTask<T> extends Task {

    private ResponseListener<T> callback;
    private ObjectMappingFactory<T> objectMappingFactory;
    private ConnectivityManager connectivityManager;

    public ApiTask(
        ResponseListener<T> callback,
        ConnectivityManager connectivityManager,
        ObjectMappingFactory<T> objectMappingFactory
    ) {
        this.objectMappingFactory = objectMappingFactory;
        this.callback = callback;
        this.connectivityManager = connectivityManager;
    }

    @Override
    protected void onResponse(InputStream stream) {
        try (JsonReader reader = new JsonReader(new InputStreamReader(stream))) {
            T model = objectMappingFactory.instantiate(reader);
            callback.onResponse(model);
        } catch (Exception e) {
            callback.onFailure(new GuestlogixException(e.getMessage(), ErrorCode.JSON_PARSING));
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
}
