package com.niden.unsplash.PhotoDetail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.niden.unsplash.PhotosGrid.PhotoViewModel

import com.niden.unsplash.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_photo_detail.*
import kotlinx.android.synthetic.main.row_photo.view.*

/**
 * A simple [Fragment] subclass.
 *
 */
class PhotoDetailFragment(private val photo: PhotoViewModel) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageView = view.findViewById<ImageView>(R.id.imageDetailView)
        Picasso.get().load(photo.urlRegular).into(imageView)
        textDetailView.text = photo.description
    }

}
