package com.niden.unsplash.PhotosGrid

import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.niden.unsplash.Network.PhotoApiModel
import com.niden.unsplash.PhotoActivity
import com.niden.unsplash.R
import com.niden.unsplash.inflate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_grid_photo.view.*

// Adapter required for recycler view
class PhotosGridAdapter: RecyclerView.Adapter<PhotosGridAdapter.ViewHolder>() {

    var photos: List<PhotoApiModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = parent.inflate(R.layout.item_grid_photo, false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
       return photos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemPhoto = photos[position]
        Log.i("bind", itemPhoto.id)
        //holder.bindPhoto(itemPhoto)
        //holder.view.text = "testing"
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun updateList(photos: List<PhotoApiModel>) {
        this.photos = photos
        notifyDataSetChanged()
    }

}