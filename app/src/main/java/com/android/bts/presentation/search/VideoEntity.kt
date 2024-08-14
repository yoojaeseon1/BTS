package com.android.bts.presentation.search

import com.android.bts.data.remote.Items
import com.android.bts.data.remote.Snippet
import com.android.bts.data.remote.VideoResponse
import java.util.UUID

data class VideoEntity(
    val uId: String = UUID.randomUUID().toString(),
    val kind: String,
    val etag: String,
    val nextPageToken: String,
    val regionCode: String,
    val items: List<ItemsEntity>?
)

data class ItemsEntity(
    val snippet: SnippetEntity
)

data class SnippetEntity(
    val publishedAt: String,
    val channelId: String,
    val title: String,
    val description: String,
    val publishTime: String,
    val channelTitle: String,
)

fun toVideoEntity(content: VideoResponse): VideoEntity = with(content) {
    return VideoEntity(
        kind = kind,
        etag = etag,
        nextPageToken = nextPageToken,
        regionCode = regionCode,
        items = toItemEntity(items)
    )
}

fun toItemEntity(items: List<Items>?): List<ItemsEntity> = with(items) {
    return this?.map { items ->
        ItemsEntity(
            snippet = toSnippetEntity(items.snippet)
        )
    }.orEmpty()
}

fun toSnippetEntity(snippet: Snippet): SnippetEntity = with(snippet) {
    return SnippetEntity(
        publishedAt,
        channelId,
        title,
        description,
        publishTime,
        channelTitle
    )
}
