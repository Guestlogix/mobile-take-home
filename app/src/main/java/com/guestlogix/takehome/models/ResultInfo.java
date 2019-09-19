package com.guestlogix.takehome.models;

import android.util.JsonReader;

import com.guestlogix.takehome.network.response.ObjectMappingFactory;

public class ResultInfo {
    private int count;
    private int pages;


    public ResultInfo(int count, int pages) {
        this.count = count;
        this.pages = pages;
    }

    public int getCount() {
        return count;
    }

    public int getPages() {
        return pages;
    }

    public static class ResultInfoObjectMappingFactory implements ObjectMappingFactory<ResultInfo> {

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
}
