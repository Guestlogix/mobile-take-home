package com.guestlogix.takehome.viewmodels.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.guestlogix.takehome.data.Character;
import com.guestlogix.takehome.viewmodels.CharacterDetailViewModel;

public class CharacterDetailViewModelFactory implements ViewModelProvider.Factory {
        private Character character;

        public CharacterDetailViewModelFactory(Character character) {
            this.character = character;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new CharacterDetailViewModel(character);
        }
    }