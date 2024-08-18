package com.android.bts.presentation.search

interface SearchRepository {
    suspend fun getSearchVideoList(searchWord : String) : VideoEntity

    suspend fun getNextSearchVideoList(searchWord : String, nextPageToken : String) : VideoEntity


}