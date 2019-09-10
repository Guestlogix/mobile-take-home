package com.guestlogix.takehome.datasource;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class EpisodesDataFactory extends DataSource.Factory {

    private MutableLiveData<EpisodesDataSource> mutableLiveData;

    public EpisodesDataFactory() {
        this.mutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {
        EpisodesDataSource feedDataSource = new EpisodesDataSource();
        mutableLiveData.postValue(feedDataSource);
        return feedDataSource;
    }
  
    public MutableLiveData<EpisodesDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}