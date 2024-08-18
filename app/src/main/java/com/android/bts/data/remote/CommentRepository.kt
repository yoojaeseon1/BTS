package com.android.bts.data.remote

class CommentRepository(private val apiService: CommentRemoteDataSource) {
    suspend fun getVideoComments(videoId: String, apiKey: String): List<CommentItem> {
        return apiService.getVideoComments(videoId = videoId, apiKey = apiKey).items
    }
}
