package com.android.bts.presentation

import com.android.bts.data.RetrofitInterface
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://www.googleapis.com/youtube/v3/"

    val apiService: RetrofitInterface
        get() = instance.create(RetrofitInterface::class.java)//인스턴스화

    // Retrofit 인스턴스
    private val instance: Retrofit
        get() {
            val gson = GsonBuilder().setLenient().create()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
}

