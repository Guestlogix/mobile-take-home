package com.guestlogix.takehome.network.response;

public interface JsonObjectMapperCallback<T> {
    void onSuccess(T responseObject);
    void onError(Error error);
}