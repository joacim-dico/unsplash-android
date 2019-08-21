package com.niden.unsplash.PhotosGrid

import android.util.Log
import com.niden.unsplash.MainActivity
import com.niden.unsplash.Network.PhotoApiModel
import com.niden.unsplash.Network.UnsplashApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotosGridPresenter(private val activity: MainActivity) {

    /**
     * Only used for searching, current page from Unsplash search result
     */
    var currentPage: Int = 0

    /**
     * Current query in search
     */
    var currentQuery: String = ""

    var totalPages: Int = 0

    init {
        getPhotos()
    }

    /**
     * Fetching all photos
     */
    private fun getPhotos(){
        val data = UnsplashApi.retrofitService.getPhotos()
        enqueue(data) {

            val list = it?.map { model -> PhotoViewModel(model) } ?: listOf()

            val view = PhotosGridViewModel(0,
                0,
                0,
                "",
                list
            )
            activity.updateView(view)
        }

    }

    /**
     * Query photos (send a query to unsplash)
     */
    fun queryPhotos(query: String, page: Int, perPage: Int = 20) {

        val data = UnsplashApi.retrofitService.queryPhotos(query, page, perPage)

        currentPage = page
        currentQuery = query

        enqueue(data) { result ->
            result?.let {

                val list = it.results.map { model -> PhotoViewModel(model) }

                val view = PhotosGridViewModel(currentPage,
                    it.total,
                    it.totalPages,
                    currentQuery,
                    list
                )

                totalPages = it.totalPages
                activity.updateView(view)
            }
        }
    }

    fun paginateUp() {
        if (currentPage < totalPages) {
            currentPage++
            queryPhotos(currentQuery, currentPage)
        }
    }

    fun paginateDown() {
        if (currentPage > 1) {
            currentPage--
            queryPhotos(currentQuery, currentPage)
        }
    }

    // The enqueue function is built in in the Retrofit. You can use that directly on a Call object.
    // In production this will probably be in an APIManager or similar.
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

    private fun error() {
        Log.i("Parse error", "Parse failed")
    }
}
