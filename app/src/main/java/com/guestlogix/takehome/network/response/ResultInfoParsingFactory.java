package com.guestlogix.takehome.network.response;

import android.util.JsonReader;

import com.guestlogix.takehome.models.ResultInfo;

public class ResultInfoParsingFactory implements ObjectFactory<ResultInfo> {

    @Override
    public ResultInfo instantiate(JsonReader reader) throws Exception {
        int count = -1;
        int page = -1;

        reader.beginObject();

        while (reader.hasNext()) {
            String key = reader.nextName();
            switch (key) {
                case "count":
                    count = reader.nextInt();
                    break;
                case "pages":
                    page = reader.nextInt();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }

        reader.endObject();

        return new ResultInfo(count, page);
    }
}