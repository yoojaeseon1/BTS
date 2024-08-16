package com.android.bts.data.remote

import com.android.bts.presentation.home.HomeVideoRepository
import com.android.bts.presentation.search.VideoEntity
import com.android.bts.presentation.search.toVideoEntity

//class HomeVideoRepositoryImpl(private val remoteDataSource: HomeVideoRemoteDataSource) : HomeVideoRepository{
class HomeVideoRepositoryImpl(private val remoteDataSource: SearchVideoRemoteDataSource) : HomeVideoRepository{

//    override suspend fun getHotVideoList(): VideoEntity {
//        return toVideoEntity(remoteDataSource.getHotVideos())
//    }
//
//    override suspend fun getNewVideoList(): VideoEntity {
//        return toHomeVideoEntity(remoteDataSource.getNewVideos())
//    }

    override suspend fun getHotVideoList(query: String): VideoEntity {
        return toVideoEntity(remoteDataSource.getHotVideo(query))
    }

    override suspend fun getNewVideoList(query: String, nextPageToken: String): VideoEntity {
        return toVideoEntity(remoteDataSource.getNewVideo(query, nextPageToken))
    }
}