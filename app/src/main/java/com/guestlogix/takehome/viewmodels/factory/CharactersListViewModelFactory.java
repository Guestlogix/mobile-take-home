package com.guestlogix.takehome.viewmodels.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.guestlogix.takehome.viewmodels.CharactersListViewModel;

public class CharactersListViewModelFactory implements ViewModelProvider.Factory {
        private String[] characterIds;

        public CharactersListViewModelFactory(String[] characterIds) {
            this.characterIds = characterIds;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new CharactersListViewModel(characterIds);
        }
    }