package com.niden.unsplash.PhotosGrid

import com.niden.unsplash.Network.PhotoApiModel
import com.niden.unsplash.Network.UnsplashApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotosGridPresenter(val fragment: PhotosGridFragment) {

    init {
        val data: Call<List<PhotoApiModel>> = UnsplashApi.retrofitService.getPhotos()

        data.enqueue(object : Callback<List<PhotoApiModel>> {
            override fun onFailure(call: Call<List<PhotoApiModel>>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
