package com.niden.unsplash.PhotoDetail

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.niden.unsplash.R
import com.niden.unsplash.toUri
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_photo_detail.*

class PhotoDetailActivity : AppCompatActivity() {

    private var description: String? = null
    private lateinit var photoUrl: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_photo_detail)

        photoUrl = intent.getStringExtra("photoUrl").toUri()
        description = intent.getStringExtra("photoDescription")

        initContent()
    }

    private fun initContent() {
        val imageView = findViewById<ImageView>(R.id.imageDetailView)
        Picasso.get().load(photoUrl).into(imageView)
        description?.let {
            textDetailView.text = it
        }
    }
}