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
                                      @NonNull CharactersDao charactersDao) {
        mAppExecutors = appExecutors;
        mCharactersDao = charactersDao;
    }

    public static CharactersLocalDataSource getInstance(@NonNull AppExecutors appExecutors,
                                                        @NonNull CharactersDao charactersDao) {
        if (INSTANCE == null) {
            synchronized (CharactersLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CharactersLocalDataSource(appExecutors, charactersDao);
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
    public void saveCharacter(@NonNull final Character character) {
        checkNotNull(character);
        Runnable saveRunnable = () -> mCharactersDao.insertCharacter(character);
        mAppExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public void refreshCharacters() {
        // Not required
    }

    @Override
    public void deleteAllCharacters() {
        Runnable deleteRunnable = () -> mCharactersDao.deleteCharacters();
        mAppExecutors.diskIO().execute(deleteRunnable);
    }

    @Override
    public void killCharacter(String id) {
        checkNotNull(id);
        Runnable saveRunnable = () -> mCharactersDao.killCharacter(id);
        mAppExecutors.diskIO().execute(saveRunnable);
    }

    /**
     * Note: {@link GetCharacterCallback#onDataNotAvailable()} is fired if the {@link Character} isn't
     * found.
     */
    @Override
    public void getCharacter(@NonNull final String id, @NonNull final GetCharacterCallback callback) {
        Runnable runnable = () -> {
            final Character character = mCharactersDao.getCharacterById(id);

            mAppExecutors.mainThread().execute(() -> {
                if (character != null) {
                    callback.onCharacterLoaded(character);
                } else {
                    callback.onDataNotAvailable();
                }
            });
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @VisibleForTesting
    static void clearInstance() {
        INSTANCE = null;
    }
}
