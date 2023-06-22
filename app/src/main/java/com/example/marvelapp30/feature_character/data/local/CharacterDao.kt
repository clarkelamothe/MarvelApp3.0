package com.example.marvelapp30.feature_character.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface CharacterDao {
    @Upsert
    suspend fun upsertAll(characters: List<CharacterEntity>)

    @Query("SELECT * FROM characters")
    fun pagingSource(): PagingSource<Int, CharacterEntity>

    @Query("DELETE FROM characters")
    suspend fun clearAll()
}