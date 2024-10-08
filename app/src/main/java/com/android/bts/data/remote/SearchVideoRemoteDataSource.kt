package com.android.bts.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

    interface SearchVideoRemoteDataSource {

        //검색결과 받아오는 코루틴 전용 메소드
        @GET("search")
        suspend fun getSearchVideo(
            @Query("part") part:String = "snippet",
            @Query("maxResults") maxResults:Int =20,
            @Query("pageToken") pageToken: String = "",
            @Query("type") type: String = "video",
            @Query("q") query: String,
            @Query("key") apiKey: String = "AIzaSyBGJe7YtNLzC9nZKPUlas39NQuJDrWoSuQ"
        ): VideoResponse

        @GET("search")
        suspend fun getInterestedVideo(
            @Query("q") query: String,
            @Query("part") part:String = "snippet",
            @Query("maxResults") maxResults:Int = 4,
            @Query("type") type: String = "video",
            @Query("key") apiKey: String = "AIzaSyBGJe7YtNLzC9nZKPUlas39NQuJDrWoSuQ"
        ): VideoResponse

        @GET("search")
        suspend fun getHotVideo(
            @Query("q") query: String,
            @Query("pageToken") pageToken: String = "",
            @Query("part") part:String = "snippet",
            @Query("maxResults") maxResults:Int = 4,
            @Query("order") order: String = "viewCount",
//            @Query("key") apiKey: String = "AIzaSyDJEWhqOKTHcJFagn_4_2UpS4669gXaXkk"
            @Query("key") apiKey: String = "AIzaSyBGJe7YtNLzC9nZKPUlas39NQuJDrWoSuQ"
        ): VideoResponse


        @GET("search")
        suspend fun getNewVideo(
            @Query("q") query: String,
            @Query("pageToken") pageToken: String = "",
            @Query("part") part:String = "snippet",
            @Query("maxResults") maxResults:Int =10,
            @Query("order") order: String = "date",
            @Query("type") type: String = "video",
            @Query("key") apiKey: String = "AIzaSyBGJe7YtNLzC9nZKPUlas39NQuJDrWoSuQ"
        ): VideoResponse
    }