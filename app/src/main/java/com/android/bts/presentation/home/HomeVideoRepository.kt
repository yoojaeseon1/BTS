package com.android.bts.presentation.home

import com.android.bts.presentation.search.VideoEntity

interface HomeVideoRepository {

    suspend fun getHotVideoList(query: String): VideoEntity
    suspend fun getNewVideoList(query: String, nextPageToken: String) : VideoEntity

}