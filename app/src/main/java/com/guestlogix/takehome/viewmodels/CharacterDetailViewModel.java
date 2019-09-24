package com.guestlogix.takehome.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.guestlogix.takehome.R;
import com.guestlogix.takehome.data.Character;
import com.guestlogix.takehome.data.source.CharactersDataSource;
import com.guestlogix.takehome.data.source.CharactersRepository;
import com.guestlogix.takehome.utils.SingleLiveEvent;

public class CharacterDetailViewModel extends AndroidViewModel {

    private SingleLiveEvent<Character> character = new SingleLiveEvent<>();
    public SingleLiveEvent<String> error = new SingleLiveEvent<>();
    private CharactersRepository mRepository;
    private Context mContext;

    public CharacterDetailViewModel(Application context, CharactersRepository repository) {
        super(context);

        mContext = context;
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
                error.postValue(mContext.getString(R.string.error_killing));
            }
        });
    }
}
