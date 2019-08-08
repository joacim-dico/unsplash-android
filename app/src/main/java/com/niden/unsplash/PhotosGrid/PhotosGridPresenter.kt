package com.niden.unsplash.PhotosGrid

import android.util.Log
import com.niden.unsplash.Network.PhotoApiModel
import com.niden.unsplash.Network.UnsplashApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotosGridPresenter(private val fragment: PhotosGridFragment) {

    init {
        getPhotos()
    }

    fun getPhotos(){
        val data = UnsplashApi.retrofitService.getPhotos()
        enqueue(data) { list -> list?.let { populate(it) }}
    }

    fun queryPhotos(query: String) {
        val data = UnsplashApi.retrofitService.queryPhotos(query)
        enqueue(data) { result -> result?.results?.let { populate(it) }}
    }

    private fun <T> enqueue(data: Call<T>, callback: (T?) -> Unit) {
        data.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                error()
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                callback(response.body())
            }
        })
    }

    private fun populate(list: List<PhotoApiModel>) {
        val viewModels = list.map { model -> PhotoViewModel(model) }
        fragment.populateList(viewModels)
    }

    private fun error() {
        Log.i("Parse error", "Parse failed")
    }
}
