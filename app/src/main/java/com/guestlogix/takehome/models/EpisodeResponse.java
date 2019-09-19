package com.guestlogix.takehome.models;

import android.util.JsonReader;

import com.guestlogix.takehome.network.response.ArrayMappingFactory;
import com.guestlogix.takehome.network.response.ObjectMappingFactory;

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

    public static class EpisodeResponseObjectMappingFactory implements ObjectMappingFactory<EpisodeResponse> {

        @Override
        public EpisodeResponse instantiate(JsonReader reader) throws Exception {
            ResultInfo info = null;
            List<Episode> results = null;

            reader.beginObject();

            while (reader.hasNext()) {
                String key = reader.nextName();
                switch (key) {
                    case "info":
                        info = new ResultInfo.ResultInfoObjectMappingFactory().instantiate(reader);
                        break;
                    case "results":
                        results = new ArrayMappingFactory<>(new Episode.EpisodeObjectMappingFactory()).instantiate(reader);
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }

            reader.endObject();

            return new EpisodeResponse(info, results);
        }
    }
}
