package com.example.marvelapp30.feature_comic.data.remote

class ComicService(
    private val comicApi: ComicApi
) {
    suspend fun getRemoteComicsByEvent(
        id: Int,
        limit: Int
    ) = comicApi.getComicsByEventId(id, limit)

    suspend fun getRemoteComicsByCharacter(
        characterId: Int,
        limit: Int
    ) = comicApi.getComicsByCharacterId(characterId, limit)
}