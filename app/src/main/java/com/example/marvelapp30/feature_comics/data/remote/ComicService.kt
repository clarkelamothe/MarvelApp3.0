package com.example.marvelapp30.feature_comics.data.remote

class ComicService(
    private val comicApi: ComicApi
) {
    suspend fun getRemoteComics(
        limit: Int, offset: Int
    ) = comicApi.getComics(limit, offset)
}