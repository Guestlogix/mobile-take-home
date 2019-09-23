package com.guestlogix.takehome.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.guestlogix.takehome.data.Character;
import com.guestlogix.takehome.data.source.CharactersDataSource;
import com.guestlogix.takehome.data.source.CharactersRepository;

public class CharacterDetailViewModel extends AndroidViewModel {

    private MutableLiveData<Character> character = new MutableLiveData<>();
    private CharactersRepository mRepository;

    public CharacterDetailViewModel(Application context, CharactersRepository repository) {
        super(context);
        mRepository = repository;
    }

    public void loadCharacters(Character data) {
        character.postValue(data);
    }

    public MutableLiveData<Character> getCharacter() {
        return character;
    }

    public void killCharacter(Character ref) {
        mRepository.killCharacter(ref.getId(), new CharactersDataSource.GetCharacterCallback(){

            @Override
            public void onCharacterLoaded(Character updatedData) {
                character.postValue(updatedData);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }
}
