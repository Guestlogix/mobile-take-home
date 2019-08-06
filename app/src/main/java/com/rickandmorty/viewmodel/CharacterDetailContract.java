package com.rickandmorty.viewmodel;

import com.rickandmorty.model.character.CharactersModel;

public interface CharacterDetailContract {
  interface View extends LifeCycle.View {
    void bindViews();

    void bindCharacterDetails(CharactersModel charactersModel);

    void showErrorDialog();
  }

  interface ViewModel extends LifeCycle.ViewModel {
    void loadCharacterDetails(int loadingType);
  }
}
