package com.example.rickandmorty.presentation.episodedetail.mapper;

import com.example.rickandmorty.presentation.episodedetail.model.Character;
import com.example.rickandmortydata.entity.CharacterData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;

public class CharacterViewModelMapper implements Function<List<CharacterData>, List<Character>> {
    @Override
    public List<Character> apply(List<CharacterData> characterDataList) {
        final List<Character> characters = new ArrayList<>();
        for (CharacterData characterData : characterDataList) {
            final Character character =
                    new Character(
                            characterData.getId(),
                            characterData.getName(),
                            characterData.getGender(),
                            characterData.getStatus(),
                            characterData.getImageUrl(),
                            characterData.getUrl(),
                            characterData.getOrigin().getName(),
                            characterData.getLocation().getName(),
                            characterData.getSpecies());
            characters.add(character);
        }
        return characters;
    }
}
