package com.ort.edu.parcialtp3.repository

import android.content.Context
import androidx.room.Room
import com.ort.edu.parcialtp3.database.AppDatabase
import com.ort.edu.parcialtp3.database.dao.CharacterDao
import com.ort.edu.parcialtp3.model.CharacterDB


class CharactersRepository private constructor(appDatabase: AppDatabase) {

    private val characterDao: CharacterDao = appDatabase.characterDao()

    suspend fun addCharacter(character: CharacterDB) {
        characterDao.insertCharacter(character)
    }

    suspend fun removeCharacter(character: CharacterDB) {
        characterDao.delete(character)
    }

    suspend fun getAllCharacters(): MutableList<CharacterDB> {
        return characterDao.getAll()
    }

    companion object {
        private var charactersRepository: CharactersRepository? = null

        fun getInstance(context: Context): CharactersRepository {
            return charactersRepository ?: kotlin.run {

                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "character-database"
                ).build()

                val createdCharactersRepository = CharactersRepository(db)
                charactersRepository = CharactersRepository(db)
                createdCharactersRepository
            }
        }
    }
}
