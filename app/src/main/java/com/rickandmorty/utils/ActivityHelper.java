package com.rickandmorty.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.rickandmorty.view.activity.AllEpisodesActivity;
import com.rickandmorty.view.activity.CharacterDetailActivity;
import com.rickandmorty.view.activity.EpisodeCharactersActivity;
import com.rickandmorty.view.activity.SplashActivity;

public class ActivityHelper {

  public final static String KEY_CHARACTER_IDS = "key_character_ids";
  public final static String KEY_SINGLE_CHARACTER_ID = "key_single_character_id";

  public static void openAllEpisodes(Activity context) {
    Intent intent = new Intent(context, AllEpisodesActivity.class);
    context.startActivity(intent);
    context.finish();
  }

  public static void openCharacters(Activity context, String episodeIds) {
    Intent intent = new Intent(context, EpisodeCharactersActivity.class);
    intent.putExtra(KEY_CHARACTER_IDS, episodeIds);
    context.startActivity(intent);
  }

  public static void restartLogin(Context context) {
    Intent intent = new Intent(context, SplashActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    context.startActivity(intent);
  }

  public static void openCharacterDetails(Activity context, int characterId) {
    Intent intent = new Intent(context, CharacterDetailActivity.class);
    intent.putExtra(KEY_SINGLE_CHARACTER_ID, characterId);
    context.startActivity(intent);
  }
}
