package com.android.bts.data.remote

import com.google.gson.annotations.SerializedName

data class CommentResponse(
    @SerializedName("items")
    val items: List<CommentItem>
)

data class CommentItem(
    @SerializedName("snippet")
    val snippet: CommentSnippet
)

data class CommentSnippet(
    @SerializedName("topLevelComment")
    val topLevelComment: TopLevelComment
)

data class TopLevelComment(
    @SerializedName("snippet")
    val snippet: CommentSnippetDetails
)

data class CommentSnippetDetails(
    @SerializedName("textDisplay")
    val textDisplay: String,
    @SerializedName("authorDisplayName")
    val authorDisplayName: String,
    @SerializedName("publishedAt")
    val publishedAt: String
)
