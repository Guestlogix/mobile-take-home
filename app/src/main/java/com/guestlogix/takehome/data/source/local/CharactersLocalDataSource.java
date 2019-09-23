package com.guestlogix.takehome.data.source.local;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.guestlogix.takehome.data.Character;
import com.guestlogix.takehome.data.source.CharactersDataSource;
import com.guestlogix.takehome.utils.AppExecutors;

import java.util.List;

import static androidx.core.util.Preconditions.checkNotNull;

/**
 * Concrete implementation of a data source as a db.
 */
public class CharactersLocalDataSource implements CharactersDataSource {

    private static volatile CharactersLocalDataSource INSTANCE;

    private CharactersDao mCharactersDao;

    private AppExecutors mAppExecutors;

    // Prevent direct instantiation.
    private CharactersLocalDataSource(@NonNull AppExecutors appExecutors,
                                      @NonNull CharactersDao tasksDao) {
        mAppExecutors = appExecutors;
        mCharactersDao = tasksDao;
    }

    public static CharactersLocalDataSource getInstance(@NonNull AppExecutors appExecutors,
                                                        @NonNull CharactersDao tasksDao) {
        if (INSTANCE == null) {
            synchronized (CharactersLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CharactersLocalDataSource(appExecutors, tasksDao);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Note: {@link LoadCharactersCallback#onDataNotAvailable()} is fired if the database doesn't exist
     * or the table is empty.
     */
    @Override
    public void getCharacters(String ids, @NonNull final LoadCharactersCallback callback) {
        Runnable runnable = () -> {
            final List<Character> episodes = mCharactersDao.getCharacters();
            mAppExecutors.mainThread().execute(() -> {
                if (episodes.isEmpty()) {
                    // This will be called if the table is new or just empty.
                    callback.onDataNotAvailable();
                } else {
                    callback.onCharactersLoaded(episodes);
                }
            });
        };

        mAppExecutors.diskIO().execute(runnable);
    }


    @Override
    public void saveCharacter(@NonNull final Character task) {
        checkNotNull(task);
        Runnable saveRunnable = () -> mCharactersDao.insertCharacter(task);
        mAppExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public void refreshCharacters() {
        // Not required because the {@link CharactersRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
    }

    @Override
    public void deleteAllCharacters() {
        Runnable deleteRunnable = () -> mCharactersDao.deleteCharacters();
        mAppExecutors.diskIO().execute(deleteRunnable);
    }

    @VisibleForTesting
    static void clearInstance() {
        INSTANCE = null;
    }
}
