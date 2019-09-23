package com.guestlogix.takehome.viewmodels;

import android.app.Application;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.guestlogix.takehome.data.Episode;
import com.guestlogix.takehome.data.factory.EpisodesDataFactory;
import com.guestlogix.takehome.data.source.EpisodesRepository;

/**
 * Exposes the data to be used in the episode list screen.
 * <p>
 * {@link BaseObservable} implements a listener registration mechanism which is notified when a
 * property changes. This is done by assigning a {@link Bindable} annotation to the property's
 * getter method.
 */
public class EpisodesListViewModel extends AndroidViewModel {

    public LiveData<PagedList<Episode>> episodes;
    public LiveData<Boolean> isLoading;

    EpisodesListViewModel(Application context, EpisodesRepository repository) {

        super(context);

        repository.deleteAllEpisodes();

        PagedList.Config pagedListConfig =
            (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(20)
                .setPageSize(20).build();

        episodes = new LivePagedListBuilder<Integer, Episode>(new EpisodesDataFactory(repository), pagedListConfig).build();
        isLoading = repository.isLoading;
    }

}
