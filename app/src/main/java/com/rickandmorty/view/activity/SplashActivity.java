package com.rickandmorty.view.activity;

import android.os.Bundle;
import com.rickandmorty.R;
import com.rickandmorty.view.fragment.SplashFragment;

public class SplashActivity extends BaseActivity {

  @Override public int getContentView() {
    return R.layout.activity_splash;
  }

  @Override public void onSetInitialData(Bundle savedInstanceState) {
    getSupportFragmentManager().beginTransaction().replace(R.id.container, new SplashFragment()).commit();
  }

  @Override public void bindingData() {

  }
}
