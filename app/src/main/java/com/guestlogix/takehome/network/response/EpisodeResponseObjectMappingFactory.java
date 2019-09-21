package com.guestlogix.takehome.network.response;

import android.util.JsonReader;

import com.guestlogix.takehome.models.Episode;
import com.guestlogix.takehome.models.EpisodeResponse;
import com.guestlogix.takehome.models.ResultInfo;

import java.util.List;

public class EpisodeResponseObjectMappingFactory implements ObjectFactory<EpisodeResponse> {

    @Override
    public EpisodeResponse instantiate(JsonReader reader) throws Exception {
        ResultInfo info = null;
        List<Episode> results = null;

        reader.beginObject();

        while (reader.hasNext()) {
            String key = reader.nextName();
            switch (key) {
                case "info":
                    info = new ResultInfoObjectMappingFactory().instantiate(reader);
                    break;
                case "results":
                    results = new ListFactory<>(new EpisodeObjectMappingFactory()).instantiate(reader);
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