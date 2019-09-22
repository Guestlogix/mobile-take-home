package com.guestlogix.takehome.data.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.guestlogix.takehome.data.source.EpisodesRepository;

public class EpisodesDataFactory extends DataSource.Factory {

    private MutableLiveData<EpisodesRepository> mutableLiveData;
    private EpisodesRepository episodesRepository;

    public EpisodesDataFactory(EpisodesRepository episodesRepository) {
        this.mutableLiveData = new MutableLiveData<>();
        this.episodesRepository = episodesRepository;
    }

    @NonNull
    @Override
    public DataSource create() {
        mutableLiveData.postValue(episodesRepository);
        return episodesRepository;
    }

    public MutableLiveData<EpisodesRepository> getMutableLiveData() {
        return mutableLiveData;
    }
}