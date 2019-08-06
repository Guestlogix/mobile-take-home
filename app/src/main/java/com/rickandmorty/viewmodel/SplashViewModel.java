package com.rickandmorty.viewmodel;

import android.os.Handler;
import androidx.annotation.NonNull;

public class SplashViewModel extends BaseViewModel implements SplashViewContract.ViewModel {

  SplashViewContract.View viewCallback;

  @Override public void onViewResumed() {
    waitAndShowProgress();
  }

  @Override public void onViewAttached(@NonNull LifeCycle.View viewCallback) {
    this.viewCallback = (SplashViewContract.View) viewCallback;
  }

  @Override public void onViewDetached() {
  }

  @Override public void waitAndShowProgress() {
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        viewCallback.navigateToAllEpisodes();
      }
    }, 3000);
  }
}
