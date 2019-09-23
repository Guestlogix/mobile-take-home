package com.guestlogix.takehome.data.source;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.guestlogix.takehome.data.Character;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static androidx.core.util.Preconditions.checkNotNull;

public class CharactersRepository implements CharactersDataSource {

    private volatile static CharactersRepository INSTANCE = null;

    private final CharactersDataSource mCharactersRemoteDataSource;

    private final CharactersDataSource mCharactersLocalDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    Map<String, Character> mCachedCharacters = new LinkedHashMap<>();

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    private boolean mCacheIsDirty = false;

    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    // Prevent direct instantiation.
    private CharactersRepository(@NonNull CharactersDataSource charactersRemoteDataSource,
                            @NonNull CharactersDataSource charactersLocalDataSource) {
        mCharactersRemoteDataSource = checkNotNull(charactersRemoteDataSource);
        mCharactersLocalDataSource = checkNotNull(charactersLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param charactersRemoteDataSource the backend data source
     * @param charactersLocalDataSource  the device storage data source
     * @return the {@link CharactersRepository} instance
     */
    public static CharactersRepository getInstance(CharactersDataSource charactersRemoteDataSource,
                                              CharactersDataSource charactersLocalDataSource) {
        if (INSTANCE == null) {
            synchronized (CharactersRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CharactersRepository(charactersRemoteDataSource, charactersLocalDataSource);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance(CharactersDataSource, CharactersDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Gets characters from cache, local data source (SQLite) or remote data source, whichever is
     * available first.
     * <p>
     * Note: {@link LoadCharactersCallback#onDataNotAvailable()} is fired if all data sources fail to
     * get the data.
     */
    @Override
    public void getCharacters(String characterIds, @NonNull final LoadCharactersCallback callback) {
        checkNotNull(callback);

        String[] ids = characterIds.split(",");
        List<Character> cachedList = new ArrayList<>();

        List<String> pendingIds = new ArrayList<>();

        for(String id: ids) {
            Character c = getCharacterWithId(id);
            if(c == null) {
                pendingIds.add(id);
            } else {
                cachedList.add(c);
            }
        }

        if(pendingIds.isEmpty()) {
            callback.onCharactersLoaded(cachedList);
        } else {
            mCharactersRemoteDataSource.getCharacters(pendingIds.toString(), new LoadCharactersCallback() {
                @Override
                public void onCharactersLoaded(List<Character> characters) {
                    refreshCache(characters);
                    refreshLocalDataSource(characters);

                    characters.addAll(cachedList);

                    callback.onCharactersLoaded(characters);
                }

                @Override
                public void onDataNotAvailable() {
                    callback.onDataNotAvailable();
                }
            });
        }
    }

    @Override
    public void saveCharacter(@NonNull Character character) {
        checkNotNull(character);
        mCharactersRemoteDataSource.saveCharacter(character);
        mCharactersLocalDataSource.saveCharacter(character);

        // Do in memory cache update to keep the app UI up to date
        if (mCachedCharacters == null) {
            mCachedCharacters = new LinkedHashMap<>();
        }
        mCachedCharacters.put(character.getId(), character);
    }

    @Override
    public void refreshCharacters() {
        mCacheIsDirty = true;
    }

    @Override
    public void deleteAllCharacters() {
        mCharactersRemoteDataSource.deleteAllCharacters();
        mCharactersLocalDataSource.deleteAllCharacters();

        if (mCachedCharacters == null) {
            mCachedCharacters = new LinkedHashMap<>();
        }
        mCachedCharacters.clear();
    }

    @Override
    public void killCharacter(String id) {
        mCharactersLocalDataSource.killCharacter(id);
        getCharacter(id, new GetCharacterCallback() {
            @Override
            public void onCharacterLoaded(Character character) {
                mCachedCharacters.put(id, character);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void getCharacter(@NonNull String id, @NonNull GetCharacterCallback callback) {
        mCharactersLocalDataSource.getCharacter(id, callback);
    }

    private void refreshCache(List<Character> characters) {
        for (Character character : characters) {
            mCachedCharacters.put(character.getId(), character);
        }
        mCacheIsDirty = false;
    }

    private void refreshLocalDataSource(List<Character> characters) {
        for (Character character : characters) {
            mCharactersLocalDataSource.saveCharacter(character);
        }
    }

    @Nullable
    private Character getCharacterWithId(@NonNull String id) {
        checkNotNull(id);
        if (mCachedCharacters == null || mCachedCharacters.isEmpty()) {
            return null;
        } else {
            return mCachedCharacters.get(id.trim());
        }
    }
}
