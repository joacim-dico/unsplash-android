package com.niden.unsplash.PhotoDetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.niden.unsplash.R
import com.niden.unsplash.toUri
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_photo_detail.*

class PhotoDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_photo_detail)

        val photoUrl = intent.getStringExtra("photoUrl").toUri()
        val description = intent.getStringExtra("photoDescription")

        Picasso.get().load(photoUrl).into(imageDetailView)
        description?.let {
            textDetailView.text = it
        }
    }
}