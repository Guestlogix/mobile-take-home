package com.guestlogix.takehome;

import android.content.Context;
import androidx.annotation.NonNull;

import com.guestlogix.takehome.data.source.CharactersRepository;
import com.guestlogix.takehome.data.source.EpisodesDataSource;
import com.guestlogix.takehome.data.source.EpisodesRepository;
import com.guestlogix.takehome.data.source.local.CharactersLocalDataSource;
import com.guestlogix.takehome.data.source.local.EpisodesLocalDataSource;
import com.guestlogix.takehome.data.source.local.GuestlogixDatabase;
import com.guestlogix.takehome.data.source.remote.CharactersRemoteDataSource;
import com.guestlogix.takehome.data.source.remote.EpisodesRemoteDataSource;
import com.guestlogix.takehome.utils.AppExecutors;

import static androidx.core.util.Preconditions.checkNotNull;

/**
 * Enables injection of production implementations for
 * {@link EpisodesDataSource} at compile time.
 */
public class Injection {

    public static EpisodesRepository provideEpisodesRepository(@NonNull Context context) {
        checkNotNull(context);
        GuestlogixDatabase database = GuestlogixDatabase.getInstance(context);
        return EpisodesRepository.getInstance(EpisodesRemoteDataSource.getInstance(),
            EpisodesLocalDataSource.getInstance(new AppExecutors(), database.episodeDao()));
    }

    public static CharactersRepository provideCharactersRepository(@NonNull Context context) {
        checkNotNull(context);
        GuestlogixDatabase database = GuestlogixDatabase.getInstance(context);
        return CharactersRepository.getInstance(CharactersRemoteDataSource.getInstance(),
            CharactersLocalDataSource.getInstance(new AppExecutors(), database.charactersDao()));
    }
}
