package com.niden.unsplash.PhotosGrid

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
import kotlinx.android.synthetic.main.fragment_photos_grid.*

class PhotosGridFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: PhotosGridAdapter
    private lateinit var containerView: View
    private lateinit var searchView: SearchView

    private lateinit var presenter: PhotosGridPresenter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Initiate menu
        setHasOptionsMenu(true)

        // Create view
        containerView = inflater.inflate(R.layout.fragment_photos_grid, container, false)
        recyclerView = containerView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
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

        }
    }

    private fun getButton(id: Int): Button {
        return containerView.findViewById<Button>(id)
    }

    private fun closeKeyboard() {
        searchView.clearFocus()
        searchView.focusable = View.NOT_FOCUSABLE
    }

    fun populateList(list: List<PhotoViewModel>) {
        viewAdapter.updateList(list)
    }

    fun showSearchResultHeader(show: Boolean) {
        containerView
            .findViewById<LinearLayout>(R.id.search_results_header)
            ?.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun updateSearchResultHeaders(page: Int, totalResults: Int = 0, totalPages: Int) {
        containerView.findViewById<TextView>(R.id.search_result_pagination).text = "Page: ${page}/${totalPages}"
        containerView.findViewById<TextView>(R.id.search_results_title).text = "Results: ${totalResults}"
    }


}