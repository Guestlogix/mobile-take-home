package com.guestlogix.takehome.models;

import android.util.JsonReader;

import com.guestlogix.takehome.network.response.ArrayMappingFactory;
import com.guestlogix.takehome.network.response.ObjectMappingFactory;

import java.util.List;

public class CharactersResponse {

    private ResultInfo info;
    private List<Character> results;

    public CharactersResponse(ResultInfo info, List<Character> results) {
        this.info = info;
        this.results = results;
    }


    public ResultInfo getInfo() {
        return info;
    }

    public List<Character> getResults() {
        return results;
    }

    public static class CharactersResponseObjectMappingFactory implements ObjectMappingFactory<CharactersResponse> {

        @Override
        public CharactersResponse instantiate(JsonReader reader) throws Exception {
            ResultInfo info = null;
            List<Character> results = null;

            reader.beginObject();

            while (reader.hasNext()) {
                String key = reader.nextName();
                switch (key) {
                    case "info":
                        info = new ResultInfo.ResultInfoObjectMappingFactory().instantiate(reader);
                        break;
                    case "results":
                        results = new ArrayMappingFactory<>(new Character.CharacterObjectMappingFactory()).instantiate(reader);
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }

            reader.endObject();

            return new CharactersResponse(info, results);
        }
    }
}
