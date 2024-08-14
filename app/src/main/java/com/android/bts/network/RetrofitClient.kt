package com.android.bts.network

import com.android.bts.data.remote.SearchVideoRemoteDataSource
import com.android.bts.data.remote.HomeVideoRemoteDataSource
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://www.googleapis.com/youtube/v3/"

    val searchVideo : SearchVideoRemoteDataSource
        get() = retrofit.create(SearchVideoRemoteDataSource::class.java)//인스턴스화

    val homeVideo : HomeVideoRemoteDataSource
        get() = retrofit.create(HomeVideoRemoteDataSource::class.java)

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val okHttpClient by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor)
            .build()
    }
}

