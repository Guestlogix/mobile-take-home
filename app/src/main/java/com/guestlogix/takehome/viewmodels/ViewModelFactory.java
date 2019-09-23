package com.guestlogix.takehome.viewmodels;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.guestlogix.takehome.Injection;
import com.guestlogix.takehome.data.source.CharactersRepository;
import com.guestlogix.takehome.data.source.EpisodesRepository;

/**
 * A creator is used to inject the product ID into the ViewModel
 * <p>
 * This creator is to showcase how to inject dependencies into ViewModels. It's not
 * actually necessary in this case, as the product ID can be passed in a public method.
 */
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @SuppressLint("StaticFieldLeak")
    private static volatile ViewModelFactory INSTANCE;

    private final Application mApplication;

    private final EpisodesRepository mEpisodesRepository;
    private final CharactersRepository mCharactersRepository;

    public static ViewModelFactory getInstance(Application application) {

        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(application,
                            Injection.provideEpisodesRepository(application.getApplicationContext()),
                            Injection.provideCharactersRepository(application.getApplicationContext())
                        );
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    private ViewModelFactory(
        Application application,
        EpisodesRepository episodeRepository,
        CharactersRepository charactersRepository
    ) {
        mApplication = application;
        mEpisodesRepository = episodeRepository;
        mCharactersRepository = charactersRepository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CharacterDetailViewModel.class)) {
            //noinspection unchecked
            return (T) new CharacterDetailViewModel(mApplication, mCharactersRepository);
        } else if (modelClass.isAssignableFrom(CharactersListViewModel.class)) {
            //noinspection unchecked
            return (T) new CharactersListViewModel(mApplication, mCharactersRepository);
        } else if (modelClass.isAssignableFrom(EpisodesListViewModel.class)) {
            //noinspection unchecked
            return (T) new EpisodesListViewModel(mApplication, mEpisodesRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
