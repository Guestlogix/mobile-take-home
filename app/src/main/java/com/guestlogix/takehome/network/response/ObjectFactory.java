package com.guestlogix.takehome.network.response;

import android.util.JsonReader;

public interface ObjectFactory<T> {
    T instantiate(JsonReader reader) throws Exception;
}
