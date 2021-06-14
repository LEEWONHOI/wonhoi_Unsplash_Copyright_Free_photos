package com.example.wonhoi_unsplash_copyright_free_photos.data

import com.example.wonhoi_unsplash_copyright_free_photos.BuildConfig
import com.example.wonhoi_unsplash_copyright_free_photos.data.models.PhotoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApiService {

    @GET("photos/random?" +
    "client_id=${BuildConfig.UNSPLASH_ACCESS_KEY}" +
    "&count=30")
    suspend fun getRandomPhotos(
        @Query("query") query : String?
    ) : Response<List<PhotoResponse>>

}