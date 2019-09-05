package com.example.rickandmortydata.repository.datasource;

import com.example.rickandmortydata.entity.CharacterData;
import com.example.rickandmortydata.entity.EpisodeListData;
import com.example.rickandmortydata.net.RestApi;

import java.util.List;

import io.reactivex.Observable;

public class RickAndMortyCloudDataSource implements RickAndMortyDataSource {

    private RestApi mRestApi;

    RickAndMortyCloudDataSource(RestApi restApi) {
        mRestApi = restApi;
    }

    @Override
    public Observable<EpisodeListData> loadEpisodeList(String url) {
        return mRestApi.episodesDataList(url);
    }

    @Override
    public Observable<List<CharacterData>> loadCharacters(String characterIds) {
        return mRestApi.characterDataList(characterIds);
    }

    @Override
    public Observable<byte[]> loadCharacterImage(String imageUrl) {
        return mRestApi.characterImageData(imageUrl);
    }
}
