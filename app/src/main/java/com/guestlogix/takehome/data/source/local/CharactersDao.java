package com.guestlogix.takehome.data.source.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.guestlogix.takehome.data.Character;

import java.util.List;


/**
 * Data Access Object for the characters table.
 */
@Dao
public interface CharactersDao {

    /**
     * Select all characters from the characters table.
     *
     * @return all characters.
     */
    @Query("SELECT * FROM Character")
    List<Character> getCharacters();

    /**
     * Insert a character in the database. If the character already exists, replace it.
     *
     * @param character the character to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCharacter(Character character);

    /**
     * Delete all characters.
     */
    @Query("DELETE FROM Character")
    void deleteCharacters();

    /**
     * killCharacter.
     */
    @Query("UPDATE Character SET status = \"Dead\" WHERE id = :id")
    void killCharacter(String id);

    /**
     * Select a character by id.
     *
     * @param id the character id.
     * @return the character with characterId.
     */
    @Query("SELECT * FROM Character WHERE id = :id")
    Character getCharacterById(String id);
}
