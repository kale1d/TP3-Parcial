package com.ort.edu.parcialtp3.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ort.edu.parcialtp3.model.CharacterDB

@Dao
interface CharacterDao {

    @Insert
    suspend fun insertCharacter(character: CharacterDB)

    @Delete
    suspend fun delete(character: CharacterDB)

    @Query("SELECT * FROM characterDB")
    suspend fun getAll(): MutableList<CharacterDB>
}