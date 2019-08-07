package com.niden.unsplash.Network

import com.niden.unsplash.PhotosGrid.PhotoViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

private const val BASE_URL = "https://api.unsplash.com"
private const val API_KEY = "Client-ID 3e7f0a7d42a3a4ce8fdc03272e73feef2fdba0440f365a37fb3edab2d74db603"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface UnsplashApiService {
    @Headers("Authorization: $API_KEY; Accept-Version: v1")
    @GET("/photos")
    fun getPhotos(): Call<List<PhotoApiModel>>
}

object UnsplashApi {
    val retrofitService: UnsplashApiService by lazy {

        retrofit.create(UnsplashApiService::class.java)
    }
}