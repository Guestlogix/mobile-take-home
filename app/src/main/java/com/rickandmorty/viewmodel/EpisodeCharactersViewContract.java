package com.rickandmorty.viewmodel;

import com.rickandmorty.model.character.CharactersModel;
import java.util.List;

public interface EpisodeCharactersViewContract {

  interface View extends LifeCycle.View {

    void bindViews();

    void bindCharactersData(List<CharactersModel> episodesResponse);

    void navigateToDetail();

    void showErrorDialog();
  }

  interface ViewModel extends LifeCycle.ViewModel {
    void loadEpisodeCharacters();
  }
}
