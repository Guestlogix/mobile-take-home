package com.rickandmorty.model;

import java.util.ArrayList;
import java.util.List;

public class EpisodesResponse {

  private InfoModel info;
  private List<EpisodeModel> results;

  public InfoModel getInfo() {
    return info;
  }

  public void setInfo(InfoModel infoModel) {
    this.info = infoModel;
  }

  public List<EpisodeModel> getResults() {
    if (results == null) results = new ArrayList<>();
    return results;
  }

  public void setResults(List<EpisodeModel> results) {
    this.results = results;
  }
}
