package com.niden.unsplash.PhotosGrid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.niden.unsplash.Network.PhotoApiModel
import com.niden.unsplash.R

class PhotosGridFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: PhotosGridAdapter
    private lateinit var containerView: View

    private lateinit var presenter: PhotosGridPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Connect presenter
        presenter = PhotosGridPresenter(this)

        // Create view
        containerView = inflater.inflate(R.layout.fragment_photos_grid, container, false)
        recyclerView = containerView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        viewAdapter = PhotosGridAdapter()
        recyclerView.adapter = viewAdapter

        return containerView

    }

    fun populateList(list: List<PhotoViewModel>) {
        viewAdapter.updateList(list)
        recyclerView.adapter = viewAdapter

    }


}