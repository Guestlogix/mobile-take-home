package com.guestlogix.takehome.data.source.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.guestlogix.takehome.data.Episode;

import java.util.List;


/**
 * Data Access Object for the Episodes table.
 */
@Dao
public interface EpisodesDao {

    /**
     * Select all Episodes from the Episodes table.
     *
     * @return all Episodes.
     */
    @Query("SELECT * FROM Episodes")
    List<Episode> getEpisodes();

    /**
     * Insert a Episode in the database. If the Episode already exists, replace it.
     *
     * @param episode the Episode to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEpisode(Episode episode);

    /**
     * Delete all Episodes.
     */
    @Query("DELETE FROM Episodes")
    void deleteEpisodes();
}
