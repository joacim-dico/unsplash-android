package com.niden.unsplash.PhotosGrid

import android.net.Uri
import com.niden.unsplash.Network.PhotoApiModel
import com.niden.unsplash.toUri

class PhotoViewModel(val apiModel: PhotoApiModel) {
    val description: String = apiModel.description
    val urlSmall: Uri = apiModel.urls.small.toUri()
    val urlRegular: Uri = apiModel.urls.regular.toUri()
}