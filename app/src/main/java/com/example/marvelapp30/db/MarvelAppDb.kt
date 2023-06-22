package com.example.marvelapp30.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.marvelapp30.feature_character.data.local.CharacterDao
import com.example.marvelapp30.feature_character.data.local.CharacterEntity

@Database(
    version = 1,
    entities = [
        CharacterEntity::class
    ],
    exportSchema = false
)
abstract class MarvelAppDb : RoomDatabase() {

    abstract fun characterDao(): CharacterDao

    companion object {
        @Volatile
        private var Instance: MarvelAppDb? = null

        fun getDatabase(context: Context): MarvelAppDb {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MarvelAppDb::class.java, "marvel-app-db")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}