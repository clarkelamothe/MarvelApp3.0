package com.example.marvelapp30.feature_character.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.marvelapp30.apiModel.ApiResult
import com.example.marvelapp30.feature_character.data.paging.CharacterPagingSource
import com.example.marvelapp30.feature_character.data.remote.CharacterService
import com.example.marvelapp30.feature_character.domain.CharacterRepository
import com.example.marvelapp30.feature_character.domain.model.Comic
import com.example.marvelapp30.utils.Constants
import com.example.marvelapp30.utils.fixComicYear
import com.example.marvelapp30.utils.getSaleDate

class CharacterRepositoryImpl(
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

    override suspend fun getComics(characterId: Int): ApiResult<List<Comic>> {
        return try {
            val comics = service.getRemoteComicsByCharacterId(characterId)
            ApiResult.Success(
                comics.body()?.data?.results?.map {
                    Comic(
                        id = it.id,
                        title = it.title,
                        year = it.dates.getSaleDate().fixComicYear()
                    )
                }
            )
        } catch (e: Exception) {
            ApiResult.Error(e)
        }
    }
}

const val PAGE_SIZE = Constants.CHARACTER_DEFAULT_PAGE_SIZE
const val PREFETCH_DISTANCE = Constants.CHARACTER_DEFAULT_PREFETCH_DISTANCE