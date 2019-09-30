package com.guestlogix.takehome.data.source;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.guestlogix.takehome.data.Episode;
import com.guestlogix.takehome.models.EpisodeRowStub;
import com.guestlogix.takehome.network.GuestlogixException;
import com.guestlogix.takehome.network.NetworkState;
import com.guestlogix.takehome.utils.DateUtils;
import com.guestlogix.takehome.utils.SingleLiveEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static androidx.core.util.Preconditions.checkNotNull;

public class EpisodesRepository extends PageKeyedDataSource<Integer, EpisodeRowStub> implements EpisodesDataSource {

    private volatile static EpisodesRepository INSTANCE = null;

    private final EpisodesDataSource mEpisodesRemoteDataSource;

    private final EpisodesDataSource mEpisodesLocalDataSource;

    private Set<Integer> cachedPages = new HashSet<>();

    public MutableLiveData<NetworkState> isLoading = new MutableLiveData<>(NetworkState.DONE);
    public SingleLiveEvent<GuestlogixException> error = new SingleLiveEvent<>();

    // Prevent direct instantiation.
    private EpisodesRepository(@NonNull EpisodesDataSource episodesRemoteDataSource,
                               @NonNull EpisodesDataSource episodesLocalDataSource) {
        mEpisodesRemoteDataSource = checkNotNull(episodesRemoteDataSource);
        mEpisodesLocalDataSource = checkNotNull(episodesLocalDataSource);
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, EpisodeRowStub> callback) {
        isLoading.postValue(NetworkState.LOADING);
        getEpisodes(1, new LoadEpisodesCallback() {
            @Override
            public void onEpisodesLoaded(List<Episode> episodes) {
                isLoading.postValue(NetworkState.DONE);
                callback.onResult(getRowResult(episodes), null, 2);
            }

            @Override
            public void onDataNotAvailable(GuestlogixException e) {
                isLoading.postValue(NetworkState.ERROR);
                error.postValue(e);
            }
        });
    }

    private List<EpisodeRowStub> getRowResult(List<Episode> episodes) {
        List<EpisodeRowStub> result = new ArrayList<>();
        for(Episode episode: episodes) {
            result.add(new EpisodeRowStub(
                episode.getName(),
                episode.getEpisode(),
                DateUtils.getFormattedDate(episode.getCreated()),
                episode
            ));
        }
        return result;
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, EpisodeRowStub> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, EpisodeRowStub> callback) {
        isLoading.postValue(NetworkState.LOADING);
        getEpisodes(params.key, new LoadEpisodesCallback() {
            @Override
            public void onEpisodesLoaded(List<Episode> episodes) {
                isLoading.postValue(NetworkState.DONE);
                callback.onResult(getRowResult(episodes), episodes.isEmpty() ? params.key : params.key+1);
            }

            @Override
            public void onDataNotAvailable(GuestlogixException e) {
                isLoading.postValue(NetworkState.ERROR);
                error.postValue(e);
            }
        });
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param episodesRemoteDataSource the backend data source
     * @param episodesLocalDataSource  the device storage data source
     * @return the {@link EpisodesRepository} instance
     */
    public static EpisodesRepository getInstance(EpisodesDataSource episodesRemoteDataSource,
                                                 EpisodesDataSource episodesLocalDataSource) {
        if (INSTANCE == null) {
            synchronized (EpisodesRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new EpisodesRepository(episodesRemoteDataSource, episodesLocalDataSource);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(EpisodesDataSource, EpisodesDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Gets episodes from cache, local data source (SQLite) or remote data source, whichever is
     * available first.
     * <p>
     * Note: {@link LoadEpisodesCallback#onDataNotAvailable(GuestlogixException)} is fired if all data sources fail to
     * get the data.
     */
    @Override
    public void getEpisodes(int page, @NonNull final LoadEpisodesCallback callback) {
        checkNotNull(callback);

        if (!cachedPages.contains(page)) {
            // If the cache is dirty we need to fetch new data from the network.
            getEpisodesFromRemoteDataSource(page, callback);
        } else {
            // Query the local storage if available. If not, query the network.
            mEpisodesLocalDataSource.getEpisodes(page, new LoadEpisodesCallback() {
                @Override
                public void onEpisodesLoaded(List<Episode> episodes) {
                    if(!episodes.isEmpty())
                        cachedPages.add(page);
                    callback.onEpisodesLoaded(episodes);
                }

                @Override
                public void onDataNotAvailable(GuestlogixException e) {
                    getEpisodesFromRemoteDataSource(page, callback);
                }
            });
        }
    }

    @Override
    public void saveEpisode(@NonNull Episode episode) {
        checkNotNull(episode);
        mEpisodesRemoteDataSource.saveEpisode(episode);
        mEpisodesLocalDataSource.saveEpisode(episode);
    }

    @Override
    public void deleteAllEpisodes() {
        mEpisodesRemoteDataSource.deleteAllEpisodes();
        mEpisodesLocalDataSource.deleteAllEpisodes();

        if (cachedPages == null) {
            cachedPages = new HashSet<>();
        }
        cachedPages.clear();
    }

    private void getEpisodesFromRemoteDataSource(int page, @NonNull final LoadEpisodesCallback callback) {

        mEpisodesRemoteDataSource.getEpisodes(page, new LoadEpisodesCallback() {
            @Override
            public void onEpisodesLoaded(List<Episode> episodes) {
                cachedPages.add(page);
                refreshLocalDataSource(episodes);
                callback.onEpisodesLoaded(episodes);
            }

            @Override
            public void onDataNotAvailable(GuestlogixException e) {
                callback.onDataNotAvailable(e);
            }
        });
    }

    private void refreshLocalDataSource(List<Episode> episodes) {
        for (Episode episode : episodes) {
            mEpisodesLocalDataSource.saveEpisode(episode);
        }
    }
}
