package com.niden.unsplash.PhotosGrid

import android.util.Log
import com.niden.unsplash.Network.PhotoApiModel
import com.niden.unsplash.Network.SearchResultModel
import com.niden.unsplash.Network.UnsplashApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotosGridPresenter(private val fragment: PhotosGridFragment) {

    init {
        getPhotos()
    }

    private fun getPhotos() = parse(UnsplashApi.retrofitService.getPhotos())

    fun queryPhotos(query: String) {
        val data = UnsplashApi.retrofitService.queryPhotos(query)
            data.enqueue(object : Callback<SearchResultModel> {
            override fun onFailure(call: Call<SearchResultModel>, t: Throwable) {
                Log.i("Parse error", "Parse failed")
            }

            override fun onResponse(call: Call<SearchResultModel>, response: Response<SearchResultModel>) {

                response.body()?.let {
                    val viewModels = it.results.map { model -> PhotoViewModel(model) }
                    fragment.populateList(viewModels)
                }
            }

        })
    }

    private fun parse(data: Call<List<PhotoApiModel>>) {
        data.enqueue(object : Callback<List<PhotoApiModel>> {
            override fun onFailure(call: Call<List<PhotoApiModel>>, t: Throwable) {
                Log.i("Parse error", "Parse failed")
            }

            override fun onResponse(call: Call<List<PhotoApiModel>>, response: Response<List<PhotoApiModel>>) {

                response.body()?.let {
                    val viewModels = it.map { model -> PhotoViewModel(model) }
                    fragment.populateList(viewModels)
                }
            }

        })
    }
}
