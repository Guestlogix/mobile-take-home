package com.rickandmorty.viewmodel;

import com.rickandmorty.model.EpisodeModel;
import java.util.List;

public interface AllEpisodesContract {

  interface View extends LifeCycle.View {

    void bindViews();

    void bindEpisodesData(List<EpisodeModel> episodesList);

    void navigateToEpisode(String episodeIds);

    void showErrorDialog();

    void hideLoadingMore();
  }

  interface ViewModel extends LifeCycle.ViewModel {
    void loadAllEpisodes(int loadingType);
  }
}
