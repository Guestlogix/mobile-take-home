package com.guestlogix.takehome.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.guestlogix.takehome.models.Character;

public class CharacterDetailViewModel extends ViewModel {

    private MutableLiveData<Character> character = new MutableLiveData<>();

    public CharacterDetailViewModel(Character data) {

        character.postValue(data);
    }

    public MutableLiveData<Character> getCharacter() {
        return character;
    }
}
