package com.example.rickandmorty.presentation.episode.model;

import java.util.ArrayList;
import java.util.List;

public class EpisodeList {

    private EpisodePaginationInfo paginationInfo;
    private List<Episode> episodes;

    public EpisodeList() {
        this.paginationInfo = null;
        this.episodes = new ArrayList<>();
    }

    public EpisodeList(EpisodePaginationInfo paginationInfo, List<Episode> episodes) {
        this.paginationInfo = paginationInfo;
        this.episodes = episodes;
    }

    public EpisodePaginationInfo getPaginationInfo() {
        return paginationInfo;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }
}
