package com.guestlogix.takehome.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.guestlogix.takehome.R;
import com.guestlogix.takehome.data.factory.EpisodesDataFactory;
import com.guestlogix.takehome.data.source.EpisodesRepository;
import com.guestlogix.takehome.models.EpisodeRowStub;
import com.guestlogix.takehome.network.NetworkState;
import com.guestlogix.takehome.utils.SingleLiveEvent;

public class EpisodesListViewModel extends AndroidViewModel {

    public LiveData<PagedList<EpisodeRowStub>> episodes;
    public LiveData<NetworkState> isLoading;
    public SingleLiveEvent<String> error;
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


//        error = Transformations.map(repository.error, e -> {
//            String message;
//            switch (e.getCode()) {
//                case NETWORK:
//                case DB_ERROR:
//                    message = context.getString(R.string.episode_network_error);
//                    break;
//
//                default:
//                    message = context.getString(R.string.episodes_data_error);
//                    break;
//            }
//
//            return message;
//        });
    }

    public void onEpisodeClicked(EpisodeRowStub episode) {
        navigateToCharacterList.postValue(episode.getReference().getCharacters());
    }
}
