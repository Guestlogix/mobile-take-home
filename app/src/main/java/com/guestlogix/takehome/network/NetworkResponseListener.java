package com.guestlogix.takehome.network;

import android.util.JsonReader;

public interface NetworkResponseListener {

    void onResponse(JsonReader reader);
    void onFailure(String message);
}
