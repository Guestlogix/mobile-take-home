package com.guestlogix.takehome.models;

import com.guestlogix.takehome.data.Episode;

import java.util.List;

public class EpisodeResponse {

    private ResultInfo info;
    private List<Episode> results;

    public EpisodeResponse(ResultInfo info, List<Episode> results) {
        this.info = info;
        this.results = results;
    }


    public ResultInfo getInfo() {
        return info;
    }

    public List<Episode> getResults() {
        return results;
    }
}
