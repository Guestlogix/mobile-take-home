package com.guestlogix.takehome.data.source.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.guestlogix.takehome.data.Episode;

/**
 * The Room Database that contains the Task table.
 */
@Database(entities = {Episode.class}, version = 1)
public abstract class GuestlogixDatabase extends RoomDatabase {

    private static GuestlogixDatabase INSTANCE;

    public abstract EpisodesDao episodeDao();

    private static final Object sLock = new Object();

    public static GuestlogixDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    GuestlogixDatabase.class, "Episode.db")
                        .build();
            }
            return INSTANCE;
        }
    }

}
