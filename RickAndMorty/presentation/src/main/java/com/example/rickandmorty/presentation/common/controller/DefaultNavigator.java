package com.example.rickandmorty.presentation.common.controller;

import android.content.Context;
import android.content.Intent;

import com.example.rickandmorty.presentation.characterdetail.CharacterDetailActivity;
import com.example.rickandmorty.presentation.episodedetail.EpisodeDetailListActivity;
import com.example.rickandmorty.presentation.episodedetail.model.Character;

import java.net.Inet4Address;

public class DefaultNavigator implements Navigator {

    private final Context mContext;
    public DefaultNavigator(final Context context) {
        mContext = context;
    }

    @Override
    public void navigateToEpisodeDetail(String characterIds) {
        Intent intent = new Intent(mContext, EpisodeDetailListActivity.class);
        intent.putExtra(EpisodeDetailListActivity.CHARACTER_IDS, characterIds);
        mContext.startActivity(intent);
    }

    @Override
    public void navigateToCharacterDetail(Character character) {
        Intent intent = new Intent(mContext, CharacterDetailActivity.class);
        intent.putExtra(CharacterDetailActivity.NAME, character.getName());
        intent.putExtra(CharacterDetailActivity.GENDER, character.getGender());
        intent.putExtra(CharacterDetailActivity.STATUS, character.getStatus());
        intent.putExtra(CharacterDetailActivity.SPECIES, character.getSpecies());
        intent.putExtra(CharacterDetailActivity.ORIGIN, character.getOrigin());
        intent.putExtra(CharacterDetailActivity.LOCATION, character.getLocation());
        intent.putExtra(CharacterDetailActivity.IMAGE_URL, character.getImageUrl());
        mContext.startActivity(intent);
    }
}
