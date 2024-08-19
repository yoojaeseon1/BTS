package com.android.bts.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

    interface SearchVideoRemoteDataSource {

        //검색결과 받아오는 코루틴 전용 메소드
        @GET("search")
        suspend fun getSearchVideo(
            @Query("part") part:String = "snippet",
            @Query("maxResults") maxResults:Int =20,
            @Query("key") apiKey: String = "AIzaSyBrhEMfHwQcNHcvtVvwQhe0fbILK7JWL14",
            @Query("pageToken") pageToken: String = "",
            @Query("q") query: String
        ): VideoResponse


        @GET("search")
        suspend fun getHotVideo(
            @Query("q") query: String,
            @Query("pageToken") pageToken: String = "",
            @Query("part") part:String = "snippet",
            @Query("maxResults") maxResults:Int = 4,
            @Query("order") order: String = "viewCount",
//            @Query("key") apiKey: String = "AIzaSyDJEWhqOKTHcJFagn_4_2UpS4669gXaXkk"
            @Query("key") apiKey: String = "AIzaSyBrhEMfHwQcNHcvtVvwQhe0fbILK7JWL14"
        ): VideoResponse


        @GET("search")
        suspend fun getNewVideo(
            @Query("q") query: String,
            @Query("pageToken") pageToken: String = "",
            @Query("part") part:String = "snippet",
            @Query("maxResults") maxResults:Int =10,
            @Query("order") order: String = "date",
//            @Query("key") apiKey: String = "AIzaSyDJEWhqOKTHcJFagn_4_2UpS4669gXaXkk"
            @Query("key") apiKey: String = "AIzaSyBrhEMfHwQcNHcvtVvwQhe0fbILK7JWL14"
        ): VideoResponse
    }