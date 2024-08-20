package com.android.bts

import android.app.Activity
import android.content.Context
import com.android.bts.presentation.save.LikedVideo
import com.android.bts.presentation.search.ItemsEntity
import com.google.gson.Gson

object BTSUtils {


    fun addLike(context: Activity, videoId: String) {
        val pref = context.getSharedPreferences("like", Context.MODE_PRIVATE)
        val edit = pref.edit()
        edit.putBoolean(videoId, true)
        edit.apply()
    }

    fun addLike(context: Activity, itemsEntity: ItemsEntity) {
        val pref = context.getSharedPreferences("like", Context.MODE_PRIVATE)
        val edit = pref.edit()
        val gson = Gson()

        val toJson = gson.toJson(itemsEntity)

        edit.putString(itemsEntity.id.videoId, toJson)
        edit.apply()
    }

    fun deleteLike(context: Activity, videoId: String) {
        val pref = context.getSharedPreferences("like", Context.MODE_PRIVATE)
        val edit = pref.edit()
        edit.remove(videoId)
        edit.apply()
    }

    fun isCheckedLike(context: Activity, videoId: String): Boolean {
        val pref = context.getSharedPreferences("like", Context.MODE_PRIVATE)
//        return pref.getBoolean(videoId, false)
        return pref.getString(videoId, "") != ""
    }

    fun selectAllLikes(context: Activity): MutableList<LikedVideo> {

        val likedVideos = mutableListOf<LikedVideo>()

        val pref = context.getSharedPreferences("like", Context.MODE_PRIVATE)
        val allItems = pref.all
        val gson = Gson()

        for (allItem in allItems) {
            val item = allItem.value
            val fromJson = gson.fromJson(item as String, ItemsEntity::class.java)
            likedVideos.add(
                LikedVideo(
                    fromJson.id.videoId,
                    fromJson.snippet.title,
                    fromJson.snippet.channelTitle,
                    fromJson.snippet.thumbnail
                )
            )
        }
        return likedVideos
    }


}