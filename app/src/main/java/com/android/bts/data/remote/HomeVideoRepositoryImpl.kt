package com.android.bts.data.remote

import com.android.bts.presentation.home.HomeVideoRepository
import com.android.bts.presentation.search.VideoEntity
import com.android.bts.presentation.search.toVideoEntity

class HomeVideoRepositoryImpl(private val remoteDataSource: SearchVideoRemoteDataSource) : HomeVideoRepository{


    override suspend fun getInterestedVideoList(query: String): VideoEntity {
        return toVideoEntity(remoteDataSource.getInterestedVideo(query))
    }

    override suspend fun getHotVideoList(query: String): VideoEntity {
        return toVideoEntity(remoteDataSource.getHotVideo(query))
    }

    override suspend fun getNewVideoList(query: String, nextPageToken: String): VideoEntity {
        return toVideoEntity(remoteDataSource.getNewVideo(query, nextPageToken))
    }
}