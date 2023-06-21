package com.example.marvelapp30.feature_character.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.marvelapp30.db.MarvelAppDb
import com.example.marvelapp30.feature_character.data.local.CharacterEntity
import com.example.marvelapp30.feature_character.data.local.mappers.toEntity
import com.example.marvelapp30.feature_character.data.remote.CharacterService
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val db: MarvelAppDb,
    private val remote: CharacterService
) : RemoteMediator<Int, CharacterEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        0
                    } else {
                        state.config.pageSize + 20
                    }
                }
            }

            val characters =
                remote.getRemoteCharacters(
                    limit = 20,
                    offset = loadKey
                ).body()?.data?.results

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.characterDao().clearAll()
                }
                val charactersEntities =
                    characters?.map {
                        it.toEntity()
                    } ?: emptyList()
                db.characterDao().upsertAll(charactersEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = characters?.isEmpty() ?: true
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}