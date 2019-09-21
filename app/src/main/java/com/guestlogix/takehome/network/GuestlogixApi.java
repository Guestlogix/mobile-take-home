package com.guestlogix.takehome.network;

import android.content.Context;
import android.net.ConnectivityManager;

import com.guestlogix.takehome.models.Character;
import com.guestlogix.takehome.models.EpisodeResponse;
import com.guestlogix.takehome.network.response.CharacterObjectMappingFactory;
import com.guestlogix.takehome.network.response.EpisodeResponseObjectMappingFactory;
import com.guestlogix.takehome.network.response.ListFactory;
import com.guestlogix.takehome.network.tasks.ApiTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuestlogixApi {

    private static final String EPISODES_LIST = "episode";
    private static final String CHARACTERS_LIST = "character";

    private static GuestlogixApi API;
    private ConnectivityManager connectivityManager;

    private GuestlogixApi(Context context) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static void initialize(Context context) {
        if (API == null) {
            API = new GuestlogixApi(context);
        }
    }

    public static GuestlogixApi getAPI() {
        return API;
    }

    public void getEpisodesList(Integer page, ResponseListener<EpisodeResponse> listener) {
        Request.UrlRequestBuilder req = new Request.UrlRequestBuilder()
                            .method(Method.GET)
                            .apiPath(EPISODES_LIST);

        if(page != null) {
            req.queryParams(getPagingParam(page));
        }

        new ApiTask<>(
            listener,
            connectivityManager,
            new EpisodeResponseObjectMappingFactory()
        ).execute(req.build());
    }

    public void getCharactersList(String[] ids, ResponseListener<List<Character>> listener) {
        Request.UrlRequestBuilder req = new Request.UrlRequestBuilder()
            .method(Method.GET)
            .apiPath(CHARACTERS_LIST);

        if(ids != null) {
            req.pathParams(ids);
        }

        new ApiTask<>(
            listener,
            connectivityManager,
            new ListFactory<>(new CharacterObjectMappingFactory())
        ).execute(req.build());
    }

    private Map<String, String> getPagingParam(int page) {
        Map<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(page));
        return params;
    }
}
