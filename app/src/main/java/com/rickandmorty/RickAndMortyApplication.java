package com.rickandmorty;

import android.content.Context;
import androidx.multidex.MultiDexApplication;
import com.facebook.drawee.backends.pipeline.Fresco;

public class RickAndMortyApplication extends MultiDexApplication {

  static RickAndMortyApplication instance;
  private static Context context;

  public static Context getContext() {
    return context;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    Fresco.initialize(this);
    Fresco.getImagePipeline().clearCaches();
    instance = this;
    context = getApplicationContext();
  }

  public static RickAndMortyApplication getInstance() {
    return instance;
  }

}
