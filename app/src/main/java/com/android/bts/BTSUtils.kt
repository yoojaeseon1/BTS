package com.android.bts

import android.app.Activity
import android.content.Context

object BTSUtils {

    fun addLike(context: Activity, videoId: String) {
        val pref = context.getSharedPreferences("like", Context.MODE_PRIVATE)
        val edit = pref.edit()
        edit.putBoolean(videoId, true)
        edit.apply()
    }

    fun deleteLike(context: Activity, videoId: String) {
        val pref = context.getSharedPreferences("like", Context.MODE_PRIVATE)
        val edit = pref.edit()
        edit.remove(videoId)
        edit.apply()
    }

    fun isCheckedLike(context: Activity, videoId: String): Boolean{
        val pref = context.getSharedPreferences("like", Context.MODE_PRIVATE)
        return pref.getBoolean(videoId, false)
    }


}