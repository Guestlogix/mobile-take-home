package com.rickandmorty.viewmodel;

import android.os.AsyncTask;
import androidx.annotation.NonNull;
import com.rickandmorty.R;
import com.rickandmorty.RickAndMortyApplication;
import com.rickandmorty.api.CallBackApi;
import com.rickandmorty.model.EpisodeModel;
import com.rickandmorty.model.EpisodesResponse;
import com.rickandmorty.repository.BaseRepository;
import com.rickandmorty.repository.RickAndMortyStorage;
import com.rickandmorty.utils.Constants;
import com.rickandmorty.utils.Util;
import java.io.IOException;
import java.util.List;
import retrofit2.Response;

public class AllEpisodesViewModel extends BaseViewModel implements AllEpisodesContract.ViewModel {

  private AllEpisodesContract.View viewCallback;
  private BaseRepository baseRepository;
  private List<EpisodeModel> episodesList;
  private boolean isFinishLoadMore;

  public List<EpisodeModel> getEpisodes() {
    return episodesList;
  }

  public void setEpisodes(List<EpisodeModel> episodes) {
    this.episodesList = episodes;
  }

  public boolean isFinishLoadMore() {
    return isFinishLoadMore;
  }

  public void setFinishLoadMore(boolean finishLoadMore) {
    isFinishLoadMore = finishLoadMore;
  }

  public AllEpisodesViewModel() {
    baseRepository = new BaseRepository(RickAndMortyApplication.getInstance());
  }

  @Override public void onViewResumed() {
    if (initial) {
      loadAllEpisodes(Constants.LOADING_INITIAL);
    } else {
      if (getEpisodes() != null) {
        viewCallback.bindEpisodesData(getEpisodes());
      }
    }
  }

  @Override public void onViewAttached(@NonNull LifeCycle.View viewCallback) {
    this.viewCallback = (AllEpisodesContract.View) viewCallback;
  }

  @Override public void onViewDetached() {

  }

  public void getCharactersFromEpisode(EpisodeModel episodeModel, int position) {
    StringBuilder episodeIds = new StringBuilder();
    for (String character : episodeModel.getCharacters()) {
      String episode = character.substring(character.lastIndexOf('/') + 1).trim();
      if (episode.equals("")) {
        episodeIds.insert(0, "");
      } else {
        episodeIds.append(episode).append(",");
      }
    }

    viewCallback.navigateToEpisode(episodeIds.toString());
  }

  @Override public void loadAllEpisodes(final int loadingType) {
    if (loadingType == Constants.LOADING_INITIAL) {
      if (getEpisodes() != null) episodesList.clear();
      setFinishLoadMore(false);
      viewCallback.hideLoadingMore();
      RickAndMortyStorage.getObjInstance().setNextPageNumber("1");
    }
    if (viewCallback == null) return;

    new AsyncTask<Void, Void, Void>() {
      Response<EpisodesResponse> responseContent;
      EpisodesResponse episodesResponse;

      @Override
      protected void onPreExecute() {
        if (loadingType == Constants.LOADING_INITIAL) {
          showProgress(viewCallback);
        }
      }

      @Override
      protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        if (viewCallback == null) {
          return;
        }
      }

      @Override
      protected void onPostExecute(Void aVoid) {
        if (viewCallback == null) return;
        if (loadingType == Constants.LOADING_INITIAL) {
          hideProgress();
        }
        if (responseContent == null) {
          return;
        } else {
          if (!responseContent.isSuccessful() || responseContent.body().getResults() == null) {
            return;
          }
          initial = false;
          episodesResponse = responseContent.body();
          if (loadingType == Constants.LOADING_INITIAL) {
            episodesList = episodesResponse.getResults();
          } else {
            episodesList.addAll(episodesResponse.getResults());
          }
          if (Util.isAllPagesLoaded(episodesResponse) || episodesResponse.getInfo().getNext().isEmpty()) {
            setFinishLoadMore(true);
          }
          RickAndMortyStorage.getObjInstance().setNextPageNumber(Util.getNextPage(episodesResponse));
        }

        viewCallback.bindEpisodesData(episodesList);
      }

      @Override
      protected Void doInBackground(Void... voids) {
        try {
          String loadPage = RickAndMortyStorage.getObjInstance().getNextPageNumber();
          responseContent = baseRepository.getAllEpisodesData(loadPage);
          if (responseContent == null) {
            cancel(true);
            return null;
          }
        } catch (IOException e) {
          viewCallback.showErrorDialog();
        }
        return null;
      }
    }.execute();
  }
}
