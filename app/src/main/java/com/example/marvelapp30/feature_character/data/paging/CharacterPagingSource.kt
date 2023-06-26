package com.example.marvelapp30.feature_character.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.marvelapp30.feature_character.data.local.CharacterEntity
import com.example.marvelapp30.feature_character.data.local.mappers.toEntity
import com.example.marvelapp30.feature_character.data.remote.CharacterService

class CharacterPagingSource(
    private val service: CharacterService,
) : PagingSource<Int, CharacterEntity>() {
    override fun getRefreshKey(
        state: PagingState<Int, CharacterEntity>
    ): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(
                anchorPosition
            )
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, CharacterEntity> {
        return try {
            val page = params.key ?: 0
            val offset = page * PAGE_SIZE
            val response = service.getRemoteCharacters(PAGE_SIZE, offset)
            val nextKey =
                if (offset >= response.body()?.data?.total!!) null
                else page + 1
            return LoadResult.Page(
                data = response.body()?.data?.results?.map {
                    it.toEntity()
                } ?: emptyList(),
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val PAGE_SIZE = 15
    }
}