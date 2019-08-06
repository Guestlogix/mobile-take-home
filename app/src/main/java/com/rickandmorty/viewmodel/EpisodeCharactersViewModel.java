package com.rickandmorty.viewmodel;

import androidx.annotation.NonNull;
import com.rickandmorty.R;
import com.rickandmorty.RickAndMortyApplication;
import com.rickandmorty.api.CallBackApi;
import com.rickandmorty.model.character.CharactersModel;
import com.rickandmorty.repository.BaseRepository;
import java.util.List;

public class EpisodeCharactersViewModel extends BaseViewModel implements EpisodeCharactersViewContract.ViewModel {

  private EpisodeCharactersViewContract.View viewCallback;
  private BaseRepository baseRepository;
  private String characterIds;

  public EpisodeCharactersViewModel() {
    baseRepository = new BaseRepository(RickAndMortyApplication.getInstance());
  }

  public void setCharacterIds(String ids) {
    this.characterIds = ids;
  }

  @Override public void loadEpisodeCharacters() {
    try {
      showProgress(viewCallback);
      baseRepository.getEpisodeCharactersData(characterIds, new CallBackApi<List<CharactersModel>, Throwable>() {
        @Override public void onSuccess(List<CharactersModel> charactersResponse) {
          if (viewCallback != null) {
            viewCallback.bindCharactersData(charactersResponse);
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
    loadEpisodeCharacters();
  }

  @Override public void onViewAttached(@NonNull LifeCycle.View viewCallback) {
    this.viewCallback = (EpisodeCharactersViewContract.View) viewCallback;
  }

  @Override public void onViewDetached() {

  }
}
