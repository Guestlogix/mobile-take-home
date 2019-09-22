/*
 *  Copyright 2017 Google Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.guestlogix.takehome.viewmodels;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.guestlogix.takehome.Injection;
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

    public static ViewModelFactory getInstance(Application application) {

        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(application,
                            Injection.provideTasksRepository(application.getApplicationContext()));
                }
            }
        }
        return INSTANCE;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        INSTANCE = null;
    }

    private ViewModelFactory(Application application, EpisodesRepository repository) {
        mApplication = application;
        mEpisodesRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
//        if (modelClass.isAssignableFrom(StatisticsViewModel.class)) {
//            //noinspection unchecked
//            return (T) new StatisticsViewModel(mApplication, mTasksRepository);
//        } else if (modelClass.isAssignableFrom(TaskDetailViewModel.class)) {
//            //noinspection unchecked
//            return (T) new TaskDetailViewModel(mApplication, mTasksRepository);
//        } else if (modelClass.isAssignableFrom(AddEditTaskViewModel.class)) {
//            //noinspection unchecked
//            return (T) new AddEditTaskViewModel(mApplication, mTasksRepository);
//        } else
        if (modelClass.isAssignableFrom(EpisodesViewModel.class)) {
            //noinspection unchecked
            return (T) new EpisodesViewModel(mApplication, mEpisodesRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
