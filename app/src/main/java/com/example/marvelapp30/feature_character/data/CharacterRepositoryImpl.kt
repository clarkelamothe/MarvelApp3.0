package com.example.marvelapp30.feature_character.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.marvelapp30.db.MarvelAppDb
import com.example.marvelapp30.feature_character.data.paging.CharacterPagingSource
import com.example.marvelapp30.feature_character.data.remote.CharacterService
import com.example.marvelapp30.feature_character.domain.CharacterRepository
import com.example.marvelapp30.utils.Constants

class CharacterRepositoryImpl(
    private val db: MarvelAppDb,
    private val service: CharacterService
) : CharacterRepository {
    override fun getCharacters() =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PREFETCH_DISTANCE,
                maxSize = PAGE_SIZE + PREFETCH_DISTANCE * 2
            ),
            pagingSourceFactory = { CharacterPagingSource(service) }
        )
}

const val PAGE_SIZE = Constants.CHARACTER_DEFAULT_PAGE_SIZE
const val PREFETCH_DISTANCE = Constants.CHARACTER_DEFAULT_PREFETCH_DISTANCE