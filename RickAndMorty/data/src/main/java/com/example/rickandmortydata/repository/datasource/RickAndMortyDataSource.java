package com.example.rickandmortydata.repository.datasource;

import com.example.rickandmortydata.entity.CharacterData;
import com.example.rickandmortydata.entity.EpisodeData;
import com.example.rickandmortydata.entity.EpisodeListData;

import java.util.List;

import io.reactivex.Observable;

public interface RickAndMortyDataSource {

    Observable<EpisodeListData> loadEpisodeList(String url);

    Observable<List<CharacterData>> loadCharacters(String characterIds);

    Observable<byte[]> loadCharacterImage(String imageUrl);
}
