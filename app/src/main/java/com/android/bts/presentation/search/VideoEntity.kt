package com.android.bts.presentation.search

import android.util.Log
import com.android.bts.data.remote.HomeVideoItems
import com.android.bts.data.remote.HomeVideoResponse
import com.android.bts.data.remote.Items
import com.android.bts.data.remote.Snippet
import com.android.bts.data.remote.VideoResponse
import java.util.UUID

data class VideoEntity(
    val kind: String,
    val etag: String,
    val nextPageToken: String,
    val items: List<ItemsEntity>

)

data class Id(
    val kind: String,
    val videoId: String
)

data class ItemsEntity(
    val id: Id,
    val snippet: SnippetEntity
)

data class SnippetEntity(
    val uId: String = UUID.randomUUID().toString(),
//    val publishedAt: String,
//    val channelId: String,
    val title: String,
//    val description: String,
//    val publishTime: String,
    val channelTitle: String,
    val thumbnail: String,
    var isLike: Boolean = false

)

fun toVideoEntity(content: VideoResponse): VideoEntity = with(content) {
    return VideoEntity(
        kind = kind,
        etag = etag,
        nextPageToken = nextPageToken,
        items = toItemEntity(items)
    )
}

//fun toHomeVideoEntity(content: HomeVideoResponse): VideoEntity = with(content) {
//    return VideoEntity(
//        kind = kind,
//        etag = etag,
//        nextPageToken = nextPageToken,
//        items = toHomeVideoItemEntity(items)
//    )
//}

fun toItemEntity(items: List<Items>?): List<ItemsEntity> = with(items) {
    return this?.map { items ->

        Log.d("VIdeoEntity", "${items}")

        ItemsEntity(
            id = Id(items.id.kind, items.id.videoId),
            snippet = toSnippetEntity(items.snippet)
        )
    }.orEmpty()
}

//fun toHomeVideoItemEntity(items: List<HomeVideoItems>?): List<ItemsEntity> = with(items) {
//    return this?.map { items ->
//        ItemsEntity(
//            snippet = toSnippetEntity(items.snippet)
//        )
//    }.orEmpty()
//}


fun toSnippetEntity(snippet: Snippet): SnippetEntity = with(snippet) {
    return SnippetEntity(
        title = snippet.title,
        channelTitle = snippet.channelTitle,
        thumbnail = snippet.thumbnails.medium.url
    )
}
