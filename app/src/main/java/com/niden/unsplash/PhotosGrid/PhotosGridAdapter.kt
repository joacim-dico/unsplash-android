package com.niden.unsplash.PhotosGrid

import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.niden.unsplash.R
import com.niden.unsplash.inflate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_photo.view.*

// Adapter required for recycler view
class PhotosGridAdapter(val onClickListener: OnClickListener): RecyclerView.Adapter<PhotosGridAdapter.ViewHolder>() {

    var photos: List<PhotoViewModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = parent.inflate(R.layout.row_photo, false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
       return photos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemPhoto = photos[position]

        holder.bindPhoto(itemPhoto)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(itemPhoto)
        }
    }

    class OnClickListener(val clickListener: (photo: PhotoViewModel) -> Unit) {
        fun onClick(photo: PhotoViewModel) = clickListener(photo)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindPhoto(photo: PhotoViewModel) {
            Picasso.get().load(photo.urlSmall).into(view.itemImage)
        }
    }

    fun updateList(photos: List<PhotoViewModel>) {
        this.photos = photos
        notifyDataSetChanged()
    }

}