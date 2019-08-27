package com.niden.unsplash.Network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "https://api.unsplash.com"
private const val API_KEY = "Client-ID 3e7f0a7d42a3a4ce8fdc03272e73feef2fdba0440f365a37fb3edab2d74db603"


interface UnsplashApiService {
    @GET("/photos")
    fun getPhotos(): Call<List<PhotoApiModel>>

    @GET("/search/photos")
    fun queryPhotos(@Query("query") query: String,
                    @Query("page") page: Int,
                    @Query("per_page") perPage: Int): Call<SearchResultModel>
}

object UnsplashApi {

    private val client = OkHttpClient().newBuilder()
        .addInterceptor(HeaderInterceptor())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .baseUrl(BASE_URL)
        .build()

    val retrofitService: UnsplashApiService by lazy {
        retrofit.create(UnsplashApiService::class.java)
    }
}

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("Authorization", "$API_KEY")
                .addHeader("Accept-Version", "v1")
                .build()
        )
    }
}