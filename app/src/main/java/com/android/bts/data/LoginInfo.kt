package com.android.bts.data

data class LoginInfo(
    var userId: String = "",
    var userPassword: String = "",
    var userNickName: String = "",
    var userRegion: String = "",
    var userProfile: Int
)