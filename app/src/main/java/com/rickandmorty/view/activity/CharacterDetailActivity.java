package com.rickandmorty.view.activity;

import android.content.Intent;
import android.os.Bundle;
import com.rickandmorty.R;
import com.rickandmorty.view.fragment.CharacterDetailFragment;

public class CharacterDetailActivity extends BaseActivity {

  private CharacterDetailFragment characterDetailFragment;

  @Override public int getContentView() {
    return R.layout.activity_character_detail;
  }

  @Override public void onSetInitialData(Bundle savedInstanceState) {
    characterDetailFragment = new CharacterDetailFragment();
    getSupportFragmentManager().beginTransaction().replace(R.id.character_detail_container, characterDetailFragment).commit();
  }

  @Override public void bindingData() {

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    characterDetailFragment.onActivityResult(requestCode, resultCode, data);
  }
}
