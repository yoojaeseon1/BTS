package com.android.bts.data.remote

import com.android.bts.presentation.home.HomeVideoRepository
import com.android.bts.presentation.search.VideoEntity
import com.android.bts.presentation.search.toHomeVideoEntity

class HomeVideoRepositoryImpl(private val remoteDataSource: HomeVideoRemoteDataSource) : HomeVideoRepository{

    override suspend fun getHotVideoList(): VideoEntity {
        return toHomeVideoEntity(remoteDataSource.getHotVideos())
    }

    override suspend fun getNewVideoList(): VideoEntity {
        return toHomeVideoEntity(remoteDataSource.getRecentVideos())
    }
}