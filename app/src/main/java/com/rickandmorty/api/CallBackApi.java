package com.rickandmorty.api;

public abstract class CallBackApi<T, K> {

  public abstract void onSuccess(T data);

  public abstract void onError(int httpCode, String errorCode, Object errorObject);

  public abstract void onFailure(K fail);

  public abstract void onExpired();

  public void onProcessError(int httpCode, String errorCode, Object errorObject) {
    onError(httpCode, errorCode, errorObject);
  }
}
