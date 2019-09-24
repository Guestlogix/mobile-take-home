package com.guestlogix.takehome.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.guestlogix.takehome.data.factory.EpisodesDataFactory;
import com.guestlogix.takehome.data.source.EpisodesRepository;
import com.guestlogix.takehome.models.EpisodeRowStub;
import com.guestlogix.takehome.utils.SingleLiveEvent;

public class EpisodesListViewModel extends AndroidViewModel {

    public LiveData<PagedList<EpisodeRowStub>> episodes;
    public LiveData<Boolean> isLoading;
    public SingleLiveEvent<String> navigateToCharacterList = new SingleLiveEvent<>();

    EpisodesListViewModel(Application context, EpisodesRepository repository) {

        super(context);

        repository.deleteAllEpisodes();

        PagedList.Config pagedListConfig =
            (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(20)
                .setPageSize(20).build();

        episodes = new LivePagedListBuilder<Integer, EpisodeRowStub>(new EpisodesDataFactory(repository), pagedListConfig).build();
        isLoading = repository.isLoading;
    }

    public void onEpisodeClicked(EpisodeRowStub episode) {
        navigateToCharacterList.postValue(episode.getReference().getCharacters());
    }
}
