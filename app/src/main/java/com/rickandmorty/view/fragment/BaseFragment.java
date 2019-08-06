package com.rickandmorty.view.fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.rickandmorty.viewmodel.LifeCycle;

public abstract class BaseFragment extends DialogFragment implements LifeCycle.View, View.OnKeyListener {

  protected abstract LifeCycle.ViewModel getViewModel();

  @Override
  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    view.setFocusableInTouchMode(true);
    view.requestFocus();
    view.setOnKeyListener(this);
  }

  @Override
  public void onResume() {
    super.onResume();
    LifeCycle.ViewModel viewModel = getViewModel();
    if (viewModel != null) viewModel.onViewResumed();
  }

  @Override
  public void onStart() {
    super.onStart();
    LifeCycle.ViewModel viewModel = getViewModel();
    if (viewModel != null) viewModel.onViewAttached(this);
  }

  @Override
  public void onStop() {
    super.onStop();
    LifeCycle.ViewModel viewModel = getViewModel();
    if (viewModel != null) viewModel.onViewDetached();
  }

  public abstract void onBackButtonPressed();

  @Override public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
    if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
      if (keyCode == KeyEvent.KEYCODE_BACK) {
        onBackButtonPressed();
        return true;
      }
    }

    return false;
  }


}
