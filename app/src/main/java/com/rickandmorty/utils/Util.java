package com.rickandmorty.utils;

import com.rickandmorty.model.EpisodesResponse;

public class Util {
  public static boolean isAllPagesLoaded(EpisodesResponse episodes) {
    String episodeLoaded = "";
    if (episodes.getInfo() != null) {
      episodeLoaded =
          episodes.getInfo().getPrev().substring(episodes.getInfo().getPrev().lastIndexOf('=') + 1).trim();
    }

    return !episodeLoaded.isEmpty() && episodeLoaded.equalsIgnoreCase("19");
  }

  public static String getNextPage(EpisodesResponse episodesResponse) {
    if (episodesResponse != null) {
      return getNextPageNumber(episodesResponse.getInfo().getNext());
    } else {
      return "1";
    }
  }

  private static String getNextPageNumber(String nextPageURL) {
    return nextPageURL.substring(nextPageURL.lastIndexOf('=') + 1).trim();
  }
}
