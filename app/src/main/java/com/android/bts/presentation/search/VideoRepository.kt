package com.android.bts.presentation.search

interface VideoRepository {
    suspend fun getVideoList(searchWord : String) : VideoEntity
}