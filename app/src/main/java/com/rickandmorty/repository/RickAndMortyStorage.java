package com.rickandmorty.repository;

import android.content.Context;
import com.rickandmorty.RickAndMortyApplication;
import java.util.HashMap;
import java.util.Map;

public class RickAndMortyStorage {

  private static final String KEY_NEXT_PAGE = "key_next_page";
  private static RickAndMortyStorage objInstance;
  private Context context;
  private Map storage;

  private RickAndMortyStorage() {
    context = RickAndMortyApplication.getContext();
  }

  public static RickAndMortyStorage getObjInstance() {
    if (objInstance == null) {
      objInstance = new RickAndMortyStorage();
    }
    return objInstance;
  }

  public void setStorage(Map storage) {
    this.storage = storage;
  }


  private Map getStorage() {
    if (storage == null) {
      storage = new HashMap();
    }
    return storage;
  }

  public void setNextPageNumber(String nextPage) {
    getStorage().put(KEY_NEXT_PAGE, nextPage);
  }

  public String getNextPageNumber() {
    return getStorage().containsKey(KEY_NEXT_PAGE) ? (String) getStorage().get(KEY_NEXT_PAGE) : "";
  }
}
