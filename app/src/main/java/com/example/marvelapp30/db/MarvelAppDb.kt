package com.example.marvelapp30.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.marvelapp30.feature_character.data.local.CharacterDao
import com.example.marvelapp30.feature_character.data.local.CharacterEntity
import com.example.marvelapp30.utils.Constants

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
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MarvelAppDb::class.java, Constants.DB_NAME)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}