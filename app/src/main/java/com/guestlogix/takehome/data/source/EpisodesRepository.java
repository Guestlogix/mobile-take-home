package com.guestlogix.takehome.data.source;

import androidx.annotation.NonNull;
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

    // Prevent direct instantiation.
    private EpisodesRepository(@NonNull EpisodesDataSource tasksRemoteDataSource,
                               @NonNull EpisodesDataSource tasksLocalDataSource) {
        mEpisodesRemoteDataSource = checkNotNull(tasksRemoteDataSource);
        mEpisodesLocalDataSource = checkNotNull(tasksLocalDataSource);
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Episode> callback) {
        getEpisodes(1, new LoadEpisodesCallback() {
            @Override
            public void onEpisodesLoaded(List<Episode> episodes) {
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
        getEpisodes(0, new LoadEpisodesCallback() {
            @Override
            public void onEpisodesLoaded(List<Episode> episodes) {
                callback.onResult(episodes, episodes.isEmpty() ? params.key : params.key+1);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param tasksRemoteDataSource the backend data source
     * @param tasksLocalDataSource  the device storage data source
     * @return the {@link EpisodesRepository} instance
     */
    public static EpisodesRepository getInstance(EpisodesDataSource tasksRemoteDataSource,
                                                 EpisodesDataSource tasksLocalDataSource) {
        if (INSTANCE == null) {
            synchronized (EpisodesRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new EpisodesRepository(tasksRemoteDataSource, tasksLocalDataSource);
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
     * Gets tasks from cache, local data source (SQLite) or remote data source, whichever is
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

    private void refreshLocalDataSource(List<Episode> tasks) {
        for (Episode task : tasks) {
            mEpisodesLocalDataSource.saveEpisode(task);
        }
    }
}
