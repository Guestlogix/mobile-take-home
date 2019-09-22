package com.guestlogix.takehome.data.source;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.guestlogix.takehome.data.Episode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static androidx.core.util.Preconditions.checkNotNull;

public class EpisodesRepository extends PageKeyedDataSource<Integer, Episode> implements EpisodesDataSource {

    private volatile static EpisodesRepository INSTANCE = null;

    private final EpisodesDataSource mEpisodesRemoteDataSource;

    private final EpisodesDataSource mEpisodesLocalDataSource;

    private Set<Integer> cachedPages = new HashSet<>();

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    // Prevent direct instantiation.
    private EpisodesRepository(@NonNull EpisodesDataSource episodesRemoteDataSource,
                               @NonNull EpisodesDataSource episodesLocalDataSource) {
        mEpisodesRemoteDataSource = checkNotNull(episodesRemoteDataSource);
        mEpisodesLocalDataSource = checkNotNull(episodesLocalDataSource);
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Episode> callback) {
        isLoading.postValue(true);
        getEpisodes(1, new LoadEpisodesCallback() {
            @Override
            public void onEpisodesLoaded(List<Episode> episodes) {
                isLoading.postValue(false);
                callback.onResult(episodes, null, 2);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Episode> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Episode> callback) {
        isLoading.postValue(true);
        getEpisodes(params.key, new LoadEpisodesCallback() {
            @Override
            public void onEpisodesLoaded(List<Episode> episodes) {
                isLoading.postValue(false);
                callback.onResult(episodes, episodes.isEmpty() ? params.key : params.key+1);
            }

            @Override
            public void onDataNotAvailable() {
                isLoading.postValue(false);
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
     * Note: {@link LoadEpisodesCallback#onDataNotAvailable()} is fired if all data sources fail to
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
                public void onDataNotAvailable() {
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
    public void refreshEpisodes() {
        cachedPages.clear();
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
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshLocalDataSource(List<Episode> episodes) {
        for (Episode episode : episodes) {
            mEpisodesLocalDataSource.saveEpisode(episode);
        }
    }
}
