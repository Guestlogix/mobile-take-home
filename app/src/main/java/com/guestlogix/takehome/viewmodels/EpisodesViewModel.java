package com.guestlogix.takehome.viewmodels;

import android.app.Application;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.guestlogix.takehome.data.Episode;
import com.guestlogix.takehome.data.factory.EpisodesDataFactory;
import com.guestlogix.takehome.data.source.EpisodesDataSource;
import com.guestlogix.takehome.data.source.EpisodesRepository;

/**
 * Exposes the data to be used in the episode list screen.
 * <p>
 * {@link BaseObservable} implements a listener registration mechanism which is notified when a
 * property changes. This is done by assigning a {@link Bindable} annotation to the property's
 * getter method.
 */
public class EpisodesViewModel extends AndroidViewModel {

    public LiveData<PagedList<Episode>> episodes = new MutableLiveData<>();

    public final ObservableBoolean dataLoading = new ObservableBoolean(false);

    public final ObservableBoolean empty = new ObservableBoolean(false);

    private final EpisodesRepository mEpisodesRepository;

    private final ObservableBoolean mIsDataLoadingError = new ObservableBoolean(false);

    public EpisodesViewModel(Application context, EpisodesRepository repository) {

        super(context);
        mEpisodesRepository = repository;
    }

    public void start() {
        loadEpisodes(false);
    }

    public void loadEpisodes(boolean forceUpdate) {
        loadEpisodes(forceUpdate, true);
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the {@link EpisodesDataSource}
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadEpisodes(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            dataLoading.set(true);
        }
        if (forceUpdate) {
            mEpisodesRepository.refreshEpisodes();
        }

        PagedList.Config pagedListConfig =
            (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(10)
                .setPageSize(10).build();

        episodes = new LivePagedListBuilder<Integer, Episode>(new EpisodesDataFactory(mEpisodesRepository), pagedListConfig).build();
    }
}
