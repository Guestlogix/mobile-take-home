//package com.guestlogix.takehome.datasource;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.MutableLiveData;
//import androidx.paging.DataSource;
//
//public class EpisodesDataFactory extends DataSource.Factory {
//
//    private MutableLiveData<EpisodesDataSource> mutableLiveData;
//
//    public EpisodesDataFactory() {
//        this.mutableLiveData = new MutableLiveData<>();
//    }
//
//    @NonNull
//    @Override
//    public DataSource create() {
//        EpisodesDataSource episodesDataSource = new EpisodesDataSource();
//        mutableLiveData.postValue(episodesDataSource);
//        return episodesDataSource;
//    }
//
//    public MutableLiveData<EpisodesDataSource> getMutableLiveData() {
//        return mutableLiveData;
//    }
//}