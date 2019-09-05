package com.example.rickandmorty.presentation.episodedetail.callback;

import android.view.View;

import com.example.rickandmorty.presentation.episodedetail.model.Character;

public interface EpisodeDetailClickListener {

    public void onClick(View view, int position, Character character);
}
