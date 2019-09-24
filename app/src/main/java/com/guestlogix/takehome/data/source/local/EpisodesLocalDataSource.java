package com.guestlogix.takehome.data.source.local;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.guestlogix.takehome.data.Episode;
import com.guestlogix.takehome.data.source.EpisodesDataSource;
import com.guestlogix.takehome.network.ErrorCode;
import com.guestlogix.takehome.network.GuestlogixException;
import com.guestlogix.takehome.utils.AppExecutors;

import java.util.List;

import static androidx.core.util.Preconditions.checkNotNull;

/**
 * Concrete implementation of a data source as a db.
 */
public class EpisodesLocalDataSource implements EpisodesDataSource {

    private static volatile EpisodesLocalDataSource INSTANCE;

    private EpisodesDao mEpisodesDao;

    private AppExecutors mAppExecutors;

    // Prevent direct instantiation.
    private EpisodesLocalDataSource(@NonNull AppExecutors appExecutors,
            @NonNull EpisodesDao episodesDao) {
        mAppExecutors = appExecutors;
        mEpisodesDao = episodesDao;
    }

    public static EpisodesLocalDataSource getInstance(@NonNull AppExecutors appExecutors,
            @NonNull EpisodesDao episodesDao) {
        if (INSTANCE == null) {
            synchronized (EpisodesLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new EpisodesLocalDataSource(appExecutors, episodesDao);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Note: {@link LoadEpisodesCallback#onDataNotAvailable(GuestlogixException)} is fired if the database doesn't exist
     * or the table is empty.
     */
    @Override
    public void getEpisodes(int page, @NonNull final LoadEpisodesCallback callback) {
        Runnable runnable = () -> {
            final List<Episode> episodes = mEpisodesDao.getEpisodes();
            mAppExecutors.mainThread().execute(() -> {
                if (episodes.isEmpty()) {
                    // This will be called if the table is new or just empty.
                    callback.onDataNotAvailable(new GuestlogixException("No Data found", ErrorCode.DB_ERROR));
                } else {
                    callback.onEpisodesLoaded(episodes);
                }
            });
        };

        mAppExecutors.diskIO().execute(runnable);
    }


    @Override
    public void saveEpisode(@NonNull final Episode episode) {
        checkNotNull(episode);
        Runnable saveRunnable = () -> mEpisodesDao.insertEpisode(episode);
        mAppExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public void deleteAllEpisodes() {
        Runnable deleteRunnable = () -> mEpisodesDao.deleteEpisodes();
        mAppExecutors.diskIO().execute(deleteRunnable);
    }

    @VisibleForTesting
    static void clearInstance() {
        INSTANCE = null;
    }
}
