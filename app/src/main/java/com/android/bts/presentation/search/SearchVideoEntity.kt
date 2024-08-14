package com.android.bts.presentation.search

import com.android.bts.data.remote.Items
import com.android.bts.data.remote.Snippet
import com.android.bts.data.remote.VideoResponse
import java.util.UUID

data class SearchVideoEntity(
    val kind: String,
    val etag: String,
    val nextPageToken: String,
    val regionCode: String,
    val items: List<ItemsEntity>
)

data class ItemsEntity(
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
    val thumbnails: String
)

fun toVideoEntity(content: VideoResponse): SearchVideoEntity = with(content) {
    return SearchVideoEntity(
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
        title = snippet.title,
        channelTitle = snippet.channelTitle,
        thumbnails = snippet.thumbnails.medium.url
    )
}
