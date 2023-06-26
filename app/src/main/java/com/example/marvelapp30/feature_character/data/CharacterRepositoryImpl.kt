package com.example.marvelapp30.feature_character.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.marvelapp30.db.MarvelAppDb
import com.example.marvelapp30.feature_character.data.paging.CharacterPagingSource
import com.example.marvelapp30.feature_character.data.remote.CharacterService
import com.example.marvelapp30.feature_character.domain.CharacterRepository

class CharacterRepositoryImpl(
    private val db: MarvelAppDb,
    private val service: CharacterService
) : CharacterRepository {
    override suspend fun getCharacters() =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PREFETCH_DISTANCE,
                maxSize = PAGE_SIZE + PREFETCH_DISTANCE * 2
            ),
            pagingSourceFactory = { CharacterPagingSource(service) }
        )
}

const val PAGE_SIZE = 15
const val PREFETCH_DISTANCE = 5