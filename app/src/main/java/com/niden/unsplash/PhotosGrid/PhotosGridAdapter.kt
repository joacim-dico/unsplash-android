package com.niden.unsplash.PhotosGrid

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.niden.unsplash.R
import com.niden.unsplash.inflate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_photo.view.*

/**
 Photos grid recyclerview Adapter
 */
class PhotosGridAdapter(val onClickListener: (PhotoViewModel) -> (Unit)): RecyclerView.Adapter<PhotosGridAdapter.ViewHolder>() {

    var photos: ArrayList<PhotoViewModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = parent.inflate(R.layout.row_photo, false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemPhoto = photos[position]

        holder.bindPhoto(itemPhoto)

        holder.itemView.setOnClickListener {
            onClickListener(itemPhoto)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindPhoto(photo: PhotoViewModel) {
            Picasso.get().load(photo.urlSmall).into(itemView.itemImage)
        }
    }

    fun updateList(photos: List<PhotoViewModel>) {
        this.photos = photos as ArrayList<PhotoViewModel>
        notifyDataSetChanged()  // This is a very old method equivalent to .reloadData in iOS, kind of in efficient.
        // notifyItemRangeChanged() is a better way to update things. Skip the Range in the method to only update one row.
    }

}