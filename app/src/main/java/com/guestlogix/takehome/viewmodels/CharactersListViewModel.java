package com.guestlogix.takehome.viewmodels;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.guestlogix.takehome.datasource.CharactersDataFactory;
import com.guestlogix.takehome.datasource.CharactersDataSource;
import com.guestlogix.takehome.models.Character;
import com.guestlogix.takehome.network.NetworkState;

public class CharactersListViewModel extends ViewModel {

    private LiveData<PagedList<Character>> characters;
    private LiveData<NetworkState> networkState;

    public CharactersListViewModel() {
        CharactersDataFactory feedDataFactory = new CharactersDataFactory();
        networkState = Transformations.switchMap(feedDataFactory.getMutableLiveData(),
            (Function<CharactersDataSource, LiveData<NetworkState>>) CharactersDataSource::getNetworkState);

        PagedList.Config pagedListConfig =
            (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(10)
                .setPageSize(10).build();

        characters = new LivePagedListBuilder<Integer, Character>(feedDataFactory, pagedListConfig).build();
    }

    public LiveData<PagedList<Character>> getCharacters() {
        return characters;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }
}
