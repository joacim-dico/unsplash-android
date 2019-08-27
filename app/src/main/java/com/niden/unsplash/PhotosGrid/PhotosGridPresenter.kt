package com.niden.unsplash.PhotosGrid

import android.util.Log
import com.niden.unsplash.MainActivity
import com.niden.unsplash.Network.PhotoApiModel
import com.niden.unsplash.Network.SearchResultModel
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
    private fun getPhotos() {
        // The enqueue function is built in in the Retrofit. You can use that directly on a Call object.
        // In production this will probably be in an APIManager or similar.
        UnsplashApi.retrofitService.getPhotos().enqueue(object : Callback<List<PhotoApiModel>>{
            override fun onFailure(call: Call<List<PhotoApiModel>>, t: Throwable) {
                error()
            }

            override fun onResponse(call: Call<List<PhotoApiModel>>, response: Response<List<PhotoApiModel>>) {
                activity.updateView(response.body().parse())
            }

        })
    }

    /**
     * Query photos (send a query to unsplash)
     */
    fun queryPhotos(query: String, page: Int, perPage: Int = 20) {

        currentPage = page
        currentQuery = query

        UnsplashApi.retrofitService.queryPhotos(query, page, perPage).enqueue(object: Callback<SearchResultModel> {
            override fun onFailure(call: Call<SearchResultModel>, t: Throwable) {
                error()
            }

            override fun onResponse(call: Call<SearchResultModel>, response: Response<SearchResultModel>) {
                response.body()?.let {

                    val view = it.results.parse(currentPage,
                        it.total,
                        it.totalPages,
                        currentQuery)

                    totalPages = it.totalPages
                    activity.updateView(view)
                } ?: error()
            }
        })
    }

    fun List<PhotoApiModel>?.parse(currentPage: Int = 0, results: Int = 0, totalPages: Int = 0, currentQuery: String = ""): PhotosGridViewModel {
        val list = this?.map { model -> PhotoViewModel(model) } ?: listOf()

        return PhotosGridViewModel(currentPage,
            results,
            totalPages,
            currentQuery,
            list
        )
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

    private fun error() {
        Log.i("Parse error", "Parse failed")
    }
}
