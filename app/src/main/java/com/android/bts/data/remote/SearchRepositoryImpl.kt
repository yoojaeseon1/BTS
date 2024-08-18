package com.android.bts.data.remote

import com.android.bts.presentation.search.VideoEntity
import com.android.bts.presentation.search.SearchRepository
import com.android.bts.presentation.search.toVideoEntity


class SearchRepositoryImpl(private val searchVideoRemoteDataSource: SearchVideoRemoteDataSource) :
    SearchRepository {

    override suspend fun getSearchVideoList(searchWord : String): VideoEntity {
        return toVideoEntity(searchVideoRemoteDataSource.getSearchVideo(query = searchWord))
    }

    override suspend fun getNextSearchVideoList(searchWord: String, nextPageToken: String): VideoEntity {
        return toVideoEntity(searchVideoRemoteDataSource.getSearchVideo(query = searchWord, pageToken = nextPageToken))
    }
}