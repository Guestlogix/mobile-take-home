package com.guestlogix.takehome.network;

public interface ResponseListener<T> {

    void onResponse(T response);
    void onFailure(GuestlogixException message);
}
