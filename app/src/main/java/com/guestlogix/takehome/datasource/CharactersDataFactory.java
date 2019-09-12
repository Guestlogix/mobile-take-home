package com.guestlogix.takehome.datasource;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class CharactersDataFactory extends DataSource.Factory {

    private MutableLiveData<CharactersDataSource> mutableLiveData;

    public CharactersDataFactory() {
        this.mutableLiveData = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource create() {
        CharactersDataSource charactersDataSource = new CharactersDataSource();
        mutableLiveData.postValue(charactersDataSource);
        return charactersDataSource;
    }
  
    public MutableLiveData<CharactersDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}