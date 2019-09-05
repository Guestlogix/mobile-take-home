package com.example.rickandmortydata.repository;

import android.content.Context;

import com.example.rickandmortydata.entity.CharacterData;
import com.example.rickandmortydata.entity.EpisodeListData;
import com.example.rickandmortydata.repository.datasource.RickAndMortyDataSource;
import com.example.rickandmortydata.repository.datasource.RickAndMortyDataSourceFactory;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RickAndMortyRepositoryImpl implements RickAndMortyRepository {

    private final RickAndMortyDataSourceFactory mRickAndMortyDataSourceFactory;
    private final RickAndMortyDataSource mRickAndMortyDataSource;

    public RickAndMortyRepositoryImpl(final Context context) {
        mRickAndMortyDataSourceFactory = new RickAndMortyDataSourceFactory(context);
        mRickAndMortyDataSource = mRickAndMortyDataSourceFactory.createCloudDataSource();
    }

    @Override
    public Observable<EpisodeListData> getEpisodeByPage(String url) {
        return mRickAndMortyDataSource.loadEpisodeList(url)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<EpisodeListData> getAllEpisodes() {
        //Gets the first page by setting the page link to null
        return getEpisodeByPage(null);
    }

    @Override
    public Observable<List<CharacterData>> getCharactersWithId(String characterIds) {
        return mRickAndMortyDataSource.loadCharacters(characterIds)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<byte[]> getCharacterImageBytes(String imageUrl) {
        return mRickAndMortyDataSource.loadCharacterImage(imageUrl)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
