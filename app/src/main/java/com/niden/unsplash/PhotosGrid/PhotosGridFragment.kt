package com.niden.unsplash.PhotosGrid

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.niden.unsplash.MainActivity
import com.niden.unsplash.PhotoDetail.PhotoDetailFragment
import com.niden.unsplash.R

class PhotosGridFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: PhotosGridAdapter
    private lateinit var containerView: View
    private lateinit var searchView: SearchView

    private lateinit var presenter: PhotosGridPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Initiate menu
        setHasOptionsMenu(true)

        // Connect presenter
        presenter = PhotosGridPresenter(this)

        // Create view
        containerView = inflater.inflate(R.layout.fragment_photos_grid, container, false)
        recyclerView = containerView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        viewAdapter = PhotosGridAdapter(PhotosGridAdapter.OnClickListener {
            openDetailView(it)
        })
        recyclerView.adapter = viewAdapter

        return containerView

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {

        inflater?.inflate(R.menu.search, menu)
        initiateSearch(menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    private fun openDetailView(photo: PhotoViewModel) {
        // How do I open the detail view??
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
                    presenter.queryPhotos(query)
                    closeKeyboard()
                }
                return true
            }
        })
    }

    private fun closeKeyboard() {
        searchView.clearFocus()
        searchView.focusable = View.NOT_FOCUSABLE
    }

    fun populateList(list: List<PhotoViewModel>) {
        viewAdapter.updateList(list)
    }


}