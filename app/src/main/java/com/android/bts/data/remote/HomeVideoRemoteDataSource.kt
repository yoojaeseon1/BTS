package com.android.bts.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface HomeVideoRemoteDataSource {

    @GET("videos")
    suspend fun getHotVideos(
        @Query("part") part: String = "snippet",
        @Query("chart") chart: String = "mostPopular",
        @Query("maxResult") maxResult: String = "20",
        @Query("regionCode") regionCode: String = "KR",
        @Query("videoCategoryId") videoCategoryId: String ="20",

        @Query("key") apiKey: String = "AIzaSyDMuq3xL2VHH9YJASDegOrJszpLo5TCkkg"
    ): HomeVideoResponse
    //AIzaSyDJEWhqOKTHcJFagn_4_2UpS4669gXaXkk

    @GET("videos")
    suspend fun getNewVideos(
        @Query("part") part: String = "snippet",
        @Query("chart") chart: String = "mostPopular",
        @Query("maxResult") maxResult: String = "20",
        @Query("regionCode") regionCode: String = "KR",
        @Query("videoCategoryId") videoCategoryId: String ="10",

        @Query("key") apiKey: String = "AIzaSyDMuq3xL2VHH9YJASDegOrJszpLo5TCkkg"

    ): HomeVideoResponse





}