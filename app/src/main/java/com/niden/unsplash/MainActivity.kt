package com.niden.unsplash

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.niden.unsplash.PhotoDetail.PhotoDetailActivity
import com.niden.unsplash.PhotosGrid.*

class MainActivity : AppCompatActivity() {

    var viewState: PhotosGridViewModel? = null
    
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: PhotosGridAdapter
    private lateinit var searchView: SearchView

    private lateinit var presenter: PhotosGridPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        val layoutManager = GridLayoutManager(applicationContext, 2)
        recyclerView.layoutManager = layoutManager
        viewAdapter = PhotosGridAdapter(PhotosGridAdapter.OnClickListener {
            pushDetailView(it)
        })
        recyclerView.adapter = viewAdapter
        recyclerView.addItemDecoration(RecyclerItemDecoration(2, 0))

        initiateSearchResultButtons()

        // Connect presenter
        presenter = PhotosGridPresenter(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        initiateSearch(menu)
        return true
    }

    private fun pushDetailView(photo: PhotoViewModel) {
        val intent = Intent(this, PhotoDetailActivity::class.java)
        intent.putExtra("photoUrl", photo.urlRegular.toString())
        intent.putExtra("photoDescription", photo.description)
        startActivity(intent)
    }

    private fun initiateSearch(menu: Menu?) {

        val searchItem = menu?.let { it.findItem(R.id.action_search) }

        searchView = searchItem?.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.queryHint = "Search.."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotBlank()) {
                    presenter.queryPhotos(query, 1, 20)
                    closeKeyboard()
                }
                return true
            }
        })
    }

    private fun initiateSearchResultButtons() {
        getButton(R.id.button_next).setOnClickListener {
            presenter.paginateUp()
        }

        getButton(R.id.button_prev).setOnClickListener {
            presenter.paginateDown()
        }

    }

    private fun getButton(id: Int): Button {
        return findViewById(id)
    }

    private fun closeKeyboard() {
        searchView.clearFocus()
        searchView.focusable = View.NOT_FOCUSABLE
    }

    fun updateView(model: PhotosGridViewModel) {

        val page = model.currentPage
        val totalPages = model.totalPages
        val totalResults = model.results
        val query = model.currentQuery

        // Only show search header if query exists
        findViewById<LinearLayout>(R.id.search_results_header)
            ?.visibility = if (query != "") View.VISIBLE else View.GONE


        model.photos?.let {
            viewAdapter.updateList(it)
        }

        findViewById<TextView>(R.id.search_result_pagination).text = "Page: ${page}/${totalPages}"
        findViewById<TextView>(R.id.search_results_title).text = "Results: ${totalResults}"

    }

}
