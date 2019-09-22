package com.guestlogix.takehome.data.source.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.guestlogix.takehome.data.Episode;

import java.util.List;


/**
 * Data Access Object for the tasks table.
 */
@Dao
public interface EpisodesDao {

    /**
     * Select all tasks from the tasks table.
     *
     * @return all tasks.
     */
    @Query("SELECT * FROM Episodes")
    List<Episode> getEpisodes();

    /**
     * Insert a task in the database. If the task already exists, replace it.
     *
     * @param episode the task to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEpisode(Episode episode);

    /**
     * Delete all tasks.
     */
    @Query("DELETE FROM Episodes")
    void deleteEpisodes();
}
