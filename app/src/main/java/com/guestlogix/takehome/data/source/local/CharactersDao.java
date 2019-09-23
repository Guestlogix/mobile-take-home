package com.guestlogix.takehome.data.source.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.guestlogix.takehome.data.Character;

import java.util.List;


/**
 * Data Access Object for the tasks table.
 */
@Dao
public interface CharactersDao {

    /**
     * Select all tasks from the tasks table.
     *
     * @return all tasks.
     */
    @Query("SELECT * FROM Character")
    List<Character> getCharacters();

    /**
     * Insert a task in the database. If the task already exists, replace it.
     *
     * @param character the task to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCharacter(Character character);

    /**
     * Delete all tasks.
     */
    @Query("DELETE FROM Character")
    void deleteCharacters();
}
