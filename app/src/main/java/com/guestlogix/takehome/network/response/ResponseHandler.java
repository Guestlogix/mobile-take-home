package com.guestlogix.takehome.network.response;

import java.io.IOException;
import java.io.InputStream;

public interface ResponseHandler {
    void onHandleResponse(InputStream stream) throws IOException;
}