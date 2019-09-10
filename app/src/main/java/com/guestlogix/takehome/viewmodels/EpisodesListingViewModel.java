package com.guestlogix.takehome.viewmodels;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.guestlogix.takehome.datasource.EpisodesDataFactory;
import com.guestlogix.takehome.datasource.EpisodesDataSource;
import com.guestlogix.takehome.models.Episode;
import com.guestlogix.takehome.network.NetworkState;

public class EpisodesListingViewModel extends ViewModel {

    private LiveData<PagedList<Episode>> episodes;
    private LiveData<NetworkState> networkState;

    public EpisodesListingViewModel() {
        EpisodesDataFactory feedDataFactory = new EpisodesDataFactory();
        networkState = Transformations.switchMap(feedDataFactory.getMutableLiveData(),
                (Function<EpisodesDataSource, LiveData<NetworkState>>) EpisodesDataSource::getNetworkState);

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(10).build();

        episodes = new LivePagedListBuilder(feedDataFactory, pagedListConfig).build();
    }

    /*
     * Getter method for the network state
     */
    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<PagedList<Episode>> getEpisodes() {
        return episodes;
    }


}