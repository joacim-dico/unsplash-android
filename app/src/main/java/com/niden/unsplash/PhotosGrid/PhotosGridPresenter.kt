package com.niden.unsplash.PhotosGrid

import android.util.Log
import com.niden.unsplash.Network.PhotoApiModel
import com.niden.unsplash.Network.UnsplashApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotosGridPresenter(private val fragment: PhotosGridFragment) {

    /**
     * Only used for searching, current page from Unsplash search result
     */
    var currentPage: Int = 0

    /**
     * Current query in search
     */
    var currentQuery: String = ""

    init {
        getPhotos()
    }

    fun getPhotos(){
        fragment.showSearchResultHeader(false)
        val data = UnsplashApi.retrofitService.getPhotos()
        enqueue(data) { list ->
            list?.let { populate(it) }
        }

    }

    fun queryPhotos(query: String, page: Int, perPage: Int = 20) {

        fragment.showSearchResultHeader(true)
        val data = UnsplashApi.retrofitService.queryPhotos(query, page, perPage)

        currentPage = page
        currentQuery = query

        enqueue(data) { result ->
            result?.let {
                populateSearchHeaders(page, it.total, it.totalPages)
                populate(it.results)
            }
        }
    }

    fun paginateUp() {
        currentPage++
        queryPhotos(currentQuery, currentPage)
    }

    fun paginateDown() {
        currentPage--
        queryPhotos(currentQuery, currentPage--)
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

    /**
     * Populate the search headers, combines number of results and total pages to pagination
     */
    private fun populateSearchHeaders(page: Int, results: Int, totalPages: Int) {
        if (totalPages > 0) {
            fragment.updateSearchResultHeaders(page, results, totalPages)
        }
    }

    private fun error() {
        Log.i("Parse error", "Parse failed")
    }
}
