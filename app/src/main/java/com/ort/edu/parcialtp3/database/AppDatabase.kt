package com.ort.edu.parcialtp3.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ort.edu.parcialtp3.database.dao.CharacterDao
import com.ort.edu.parcialtp3.model.CharacterDB

@Database(entities = [CharacterDB::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}