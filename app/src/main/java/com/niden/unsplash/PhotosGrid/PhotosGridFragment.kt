package com.niden.unsplash.PhotosGrid

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.niden.unsplash.MainActivity
import com.niden.unsplash.R

class PhotosGridFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: PhotosGridAdapter
    private lateinit var containerView: View
    private lateinit var searchView: SearchView

    private lateinit var presenter: PhotosGridPresenter

    private val model: PhotosGridViewModel by lazy {
        ViewModelProviders.of(this).get(PhotosGridViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Initiate menu
        setHasOptionsMenu(true)

        // Create view
        containerView = inflater.inflate(R.layout.fragment_photos_grid, container, false)
        recyclerView = containerView.findViewById(R.id.recyclerView)

        val layoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = layoutManager
        viewAdapter = PhotosGridAdapter(PhotosGridAdapter.OnClickListener {
            openDetailView(it)
        })
        recyclerView.adapter = viewAdapter

        initiateSearchResultButtons()

        // Connect presenter
        presenter = PhotosGridPresenter(this)

        return containerView

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {

        inflater?.inflate(R.menu.search, menu)
        initiateSearch(menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    private fun openDetailView(photo: PhotoViewModel) {
        (this.activity as MainActivity).pushDetailView(photo)
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
        return containerView.findViewById(id)
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
        containerView
            .findViewById<LinearLayout>(R.id.search_results_header)
            ?.visibility = if (query != "") View.VISIBLE else View.GONE


        model.photos?.let {
            viewAdapter.updateList(it)
        }

        containerView.findViewById<TextView>(R.id.search_result_pagination).text = "Page: ${page}/${totalPages}"
        containerView.findViewById<TextView>(R.id.search_results_title).text = "Results: ${totalResults}"

    }

}