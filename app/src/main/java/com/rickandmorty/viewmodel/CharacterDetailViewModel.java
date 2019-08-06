package com.rickandmorty.viewmodel;

import androidx.annotation.NonNull;
import com.rickandmorty.RickAndMortyApplication;
import com.rickandmorty.api.CallBackApi;
import com.rickandmorty.model.character.CharactersModel;
import com.rickandmorty.repository.BaseRepository;

public class CharacterDetailViewModel extends BaseViewModel implements CharacterDetailContract.ViewModel {

  private int id;
  private CharacterDetailContract.View viewCallback;
  private BaseRepository baseRepository;

  public CharacterDetailViewModel() {
    baseRepository = new BaseRepository(RickAndMortyApplication.getInstance());
  }

  @Override public void loadCharacterDetails(int characterId) {
    try {
      showProgress(viewCallback);
      baseRepository.getCharacterDetails(characterId, new CallBackApi<CharactersModel, Throwable>() {
        @Override public void onSuccess(CharactersModel charactersModel) {
          if (viewCallback != null) {
            viewCallback.bindCharacterDetails(charactersModel);
          }
          hideProgress();
        }

        @Override public void onError(int httpCode, String errorCode, Object errorObject) {
          hideProgress();
          if (viewCallback == null) return;
          viewCallback.showErrorDialog();
        }

        @Override public void onFailure(Throwable fail) {
          hideProgress();
          if (viewCallback == null) return;
          viewCallback.showErrorDialog();
        }

        @Override public void onExpired() {
          hideProgress();
          if (viewCallback == null) return;
          viewCallback.showErrorDialog();
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override public void onViewResumed() {
    loadCharacterDetails(id);
  }

  @Override public void onViewAttached(@NonNull LifeCycle.View viewCallback) {
    this.viewCallback = (CharacterDetailContract.View) viewCallback;
  }

  @Override public void onViewDetached() {

  }

  public void setCharacterId(int characterId) {
    this.id = characterId;
  }

  public int getId() {
    return id;
  }
}
