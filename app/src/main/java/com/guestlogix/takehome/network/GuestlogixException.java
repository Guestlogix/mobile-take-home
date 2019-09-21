package com.guestlogix.takehome.network;

public class GuestlogixException extends Exception {

    private ErrorCode code;

    public GuestlogixException(String message, ErrorCode code) {
        super(message);

        this.code = code;
    }

    public ErrorCode getCode() {
        return code;
    }
}
