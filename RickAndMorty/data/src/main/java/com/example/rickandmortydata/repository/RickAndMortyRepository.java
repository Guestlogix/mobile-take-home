package com.example.rickandmortydata.repository;

import com.example.rickandmortydata.entity.CharacterData;
import com.example.rickandmortydata.entity.EpisodeData;
import com.example.rickandmortydata.entity.EpisodeListData;

import java.util.List;

import io.reactivex.Observable;

public interface RickAndMortyRepository {

    Observable<EpisodeListData> getEpisodeByPage(String url);

    Observable<EpisodeListData> getAllEpisodes();

    Observable<List<CharacterData>> getCharactersWithId(String characterIds);

    Observable<byte[]> getCharacterImageBytes(String imageUrl);
}
