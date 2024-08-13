package com.android.bts.data

import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {

    //검색결과 받아오는 코루틴 전용 메소드
    @GET("search")
    suspend fun getSearchVideo(
        @Query("part") part:String = "snippet",
        @Query("chart") chart:String = "mostPopular",
        @Query("maxResults") maxResults:Int = 20,
        @Query("key") apiKey: String = "키입력",
        @Query("q") query: String = "강아지"
    ): VideoResponse
}