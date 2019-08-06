package com.rickandmorty.viewmodel;

public interface SplashViewContract {

  interface View extends LifeCycle.View {
    void navigateToAllEpisodes();
  }

  interface ViewModel extends LifeCycle.ViewModel {
    void waitAndShowProgress();
  }
}
