package com.example.marvelapp30.feature_character.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.marvelapp30.feature_character.data.remote.CharacterService
import com.example.marvelapp30.feature_character.domain.model.Character
import com.example.marvelapp30.utils.toUrl

class CharacterPagingSource(
    private val service: CharacterService,
) : PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(
                anchorPosition
            )
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Character> {
        return try {
            val page = params.key ?: 0
            val offset = page * PAGE_SIZE
            val response = service.getRemoteCharacters(PAGE_SIZE, offset)
            val nextKey =
                if (offset >= response.body()?.data?.total!!) null
                else page + 1
            return LoadResult.Page(
                data = response.body()?.data?.results?.map {
                    Character(
                        id = it.id,
                        name = it.name,
                        imageUrl = it.thumbnail.toUrl(),
                        description = it.description
                    )
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