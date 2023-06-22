package com.example.marvelapp30.feature_character.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.marvelapp30.feature_character.data.local.CharacterDao
import com.example.marvelapp30.feature_character.data.paging.CharacterRemoteMediator
import com.example.marvelapp30.feature_character.domain.CharacterRepository

class CharacterRepositoryImpl(
    private val dao: CharacterDao,
    private val characterRemoteMediator: CharacterRemoteMediator
) : CharacterRepository {
    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getCharacters() =
        Pager(
            config = PagingConfig(
                pageSize = 30,
                prefetchDistance = 15,
                initialLoadSize = 50,
                maxSize = 90
            ),
            remoteMediator = characterRemoteMediator,
            pagingSourceFactory = { dao.pagingSource() }
        )

}