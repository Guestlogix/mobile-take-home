package com.rickandmorty.view.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getContentView());
    onSetInitialData(savedInstanceState);
  }

  public abstract int getContentView();

  public abstract void onSetInitialData(Bundle savedInstanceState);

  public abstract void bindingData();

  @Override
  protected void onResume() {
    super.onResume();
    bindingData();
  }
}
