package com.example.rickandmorty.presentation.episode.callback;

import android.view.View;

import java.util.List;

public interface EpisodeClickListener {

    void onClick(View view, int position, List<String> characterIds);
}
