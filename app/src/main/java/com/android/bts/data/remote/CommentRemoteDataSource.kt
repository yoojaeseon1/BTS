package com.android.bts.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface CommentRemoteDataSource {
    @GET("commentThreads")
    suspend fun getVideoComments(
        @Query("part") part: String = "snippet",
        @Query("videoId") videoId: String,
        @Query("key") apiKey: String,
        @Query("maxResults") maxResults: Int = 10
    ): CommentResponse
}

