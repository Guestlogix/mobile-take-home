package com.rickandmorty.api;

import com.rickandmorty.model.character.CharactersModel;
import com.rickandmorty.model.EpisodesResponse;
import java.util.List;
import retrofit2.Call;

public interface CallerAPI {

  Call<EpisodesResponse> getAllEpisodes(String page);

  Call<List<CharactersModel>> getEpisodeCharacters(String characterIds);

  Call<CharactersModel> getCharacterDetails(int characterId);
}
