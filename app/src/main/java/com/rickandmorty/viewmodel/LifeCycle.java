package com.rickandmorty.viewmodel;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

public interface LifeCycle {

  interface View {
    FragmentActivity getActivity();
  }

  interface ViewModel {
    void onViewResumed();
    void onViewAttached(@NonNull LifeCycle.View viewCallback);
    void onViewDetached();
  }
}
