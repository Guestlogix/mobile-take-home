package com.rickandmorty.view.activity;

import android.content.Intent;
import android.os.Bundle;
import com.rickandmorty.R;
import com.rickandmorty.view.fragment.EpisodeCharactersFragment;

public class EpisodeCharactersActivity extends BaseActivity {

  private EpisodeCharactersFragment episodeCharactersFragment;

  @Override public int getContentView() {
    return R.layout.activity_episode_characters;
  }

  @Override public void onSetInitialData(Bundle savedInstanceState) {
    episodeCharactersFragment = new EpisodeCharactersFragment();
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.episode_characters_container, episodeCharactersFragment)
        .commit();
  }

  @Override public void bindingData() {

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    episodeCharactersFragment.onActivityResult(requestCode, resultCode, data);
  }
}
