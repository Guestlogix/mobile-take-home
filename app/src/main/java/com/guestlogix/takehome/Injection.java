/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.guestlogix.takehome;

import android.content.Context;
import androidx.annotation.NonNull;
import com.guestlogix.takehome.data.source.EpisodesDataSource;
import com.guestlogix.takehome.data.source.EpisodesRepository;
import com.guestlogix.takehome.data.source.local.EpisodesLocalDataSource;
import com.guestlogix.takehome.data.source.local.GuestlogixDatabase;
import com.guestlogix.takehome.data.source.remote.EpisodesRemoteDataSource;
import com.guestlogix.takehome.utils.AppExecutors;

import static androidx.core.util.Preconditions.checkNotNull;

/**
 * Enables injection of production implementations for
 * {@link EpisodesDataSource} at compile time.
 */
public class Injection {

    public static EpisodesRepository provideTasksRepository(@NonNull Context context) {
        checkNotNull(context);
        GuestlogixDatabase database = GuestlogixDatabase.getInstance(context);
        return EpisodesRepository.getInstance(EpisodesRemoteDataSource.getInstance(),
            EpisodesLocalDataSource.getInstance(new AppExecutors(), database.episodeDao()));
    }
}
