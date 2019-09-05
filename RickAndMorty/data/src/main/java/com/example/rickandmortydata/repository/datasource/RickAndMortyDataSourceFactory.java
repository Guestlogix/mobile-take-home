package com.example.rickandmortydata.repository.datasource;

import android.content.Context;

import com.example.rickandmortydata.net.RestApi;
import com.example.rickandmortydata.net.RestApiImpl;

/**
 * Factory that would create different implementations of {@link RickAndMortyDataSource}
 */
public class RickAndMortyDataSourceFactory {

    private final Context mContext;

    public RickAndMortyDataSourceFactory(Context context) {
        mContext = context;
    }

    /**
     * @return {@link RickAndMortyDataSource} to retrieve data from the cloud
     */
    public RickAndMortyDataSource createCloudDataSource() {
        final RestApi restApi = new RestApiImpl(mContext);
        return new RickAndMortyCloudDataSource(restApi);
    }
}
