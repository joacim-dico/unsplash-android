package com.niden.unsplash

import android.os.Bundle
import android.provider.ContactsContract
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_photo.*

class PhotoActivity : AppCompatActivity() {

    private var selectedPhoto: ContactsContract.Contacts.Photo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_photo)

        selectedPhoto = intent.getSerializableExtra(PHOTO_KEY) as ContactsContract.Contacts.Photo
        //Picasso.get().with(this).load(selectedPhoto).into(photoImageView)

        //photoDescription?.text = selectedPhoto?
    }

    companion object {
        private val PHOTO_KEY = "PHOTO"
    }
}