package com.android.bts.presentation.home

import com.android.bts.presentation.search.VideoEntity

interface HomeVideoRepository {

    suspend fun getHotVideoList(): VideoEntity
    suspend fun getNewVideoList() : VideoEntity

}