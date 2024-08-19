package com.android.bts.data.remote

import com.android.bts.presentation.search.VideoEntity
import com.android.bts.presentation.search.VideoRepository
import com.android.bts.presentation.search.toVideoEntity


class VideoRepositoryImpl(private val searchVideoRemoteDataSource: SearchVideoRemoteDataSource) :
    VideoRepository {

    override suspend fun getVideoList(searchWord : String): VideoEntity {
        return toVideoEntity(searchVideoRemoteDataSource.getSearchVideo(query = searchWord))
    }
}