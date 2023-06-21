package com.example.marvelapp30.feature_character.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Upsert
    suspend fun upsertAll(characters: List<CharacterEntity>)

    @Query("SELECT * FROM characters")
    suspend fun pagingSource(): Flow<PagingSource<Int, CharacterEntity>>

    @Query("DELETE FROM characters")
    suspend fun clearAll()
}