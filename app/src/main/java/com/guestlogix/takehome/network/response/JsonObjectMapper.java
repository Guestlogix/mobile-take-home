package com.guestlogix.takehome.network.response;

import android.util.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonObjectMapper<T> implements ResponseHandler {
    private ObjectMappingFactory<T> objectMappingFactory;
    private JsonObjectMapperCallback<T> callback;

    public JsonObjectMapper(ObjectMappingFactory<T> objectMappingFactory, JsonObjectMapperCallback<T> callback) {
        this.objectMappingFactory = objectMappingFactory;
        this.callback = callback;
    }

    @Override
    public void onHandleResponse(InputStream stream) {
        try (JsonReader reader = new JsonReader(new InputStreamReader(stream))) {
            T model = objectMappingFactory.instantiate(reader);

            callback.onSuccess(model);
        } catch (Exception e) {
            callback.onError(new ObjectMappingError(objectMappingFactory, e));
        }
    }
}
