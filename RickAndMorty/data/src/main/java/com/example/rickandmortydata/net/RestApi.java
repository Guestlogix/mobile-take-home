package com.example.rickandmortydata.net;

import com.example.rickandmortydata.entity.CharacterData;
import com.example.rickandmortydata.entity.EpisodeData;
import com.example.rickandmortydata.entity.EpisodeListData;

import java.util.List;

import io.reactivex.Observable;

public interface RestApi {

    String BASE_URL = "https://rickandmortyapi.com/api/";
    String EPISODE_URL = BASE_URL + "episode/";
    String CHARACTER_URL = BASE_URL + "character/";

    /**
     * Retrives an {@link Observable} which will emit a list of {@link EpisodeData}
     * @param url
     */
    Observable<EpisodeListData> episodesDataList(String url);

    /**
     * Retrives an {@link Observable} which will emit a list of {@link CharacterData}
     */
    Observable<List<CharacterData>> characterDataList(String characterIds);

    /**
     * Retrives an {@link Observable} which will emit a character image
     * @param imageUrl
     * @return
     */
    Observable<byte[]> characterImageData(String imageUrl);
}
