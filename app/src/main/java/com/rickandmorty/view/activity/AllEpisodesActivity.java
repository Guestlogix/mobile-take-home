package com.rickandmorty.view.activity;

import android.content.Intent;
import android.os.Bundle;
import com.rickandmorty.R;
import com.rickandmorty.view.fragment.AllEpisodesFragment;

public class AllEpisodesActivity extends BaseActivity {

  AllEpisodesFragment allEpisodesFragment;

  @Override public int getContentView() {
    return R.layout.activity_all_episodes;
  }

  @Override public void onSetInitialData(Bundle savedInstanceState) {
    allEpisodesFragment =new AllEpisodesFragment();
    getSupportFragmentManager().beginTransaction().replace(R.id.all_episodes_container, allEpisodesFragment).commit();
  }

  @Override public void bindingData() {

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    allEpisodesFragment.onActivityResult(requestCode,resultCode,data);
  }
}
