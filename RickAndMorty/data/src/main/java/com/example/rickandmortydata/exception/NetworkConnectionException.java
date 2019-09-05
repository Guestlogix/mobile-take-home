package com.example.rickandmortydata.exception;

/**
 * Exception thrown by the application when there is an network connection exception.
 */
public class NetworkConnectionException extends Exception {

    static final String NO_NETWORK = "Network connection is unavailable";

    public NetworkConnectionException() {
        super(NO_NETWORK);
    }

}
