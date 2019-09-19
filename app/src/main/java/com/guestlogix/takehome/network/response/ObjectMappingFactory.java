package com.guestlogix.takehome.network.response;

import android.util.JsonReader;

public interface ObjectMappingFactory<T> {
    T instantiate(JsonReader reader) throws Exception;
}