package com.niden.unsplash.PhotosGrid

import com.niden.unsplash.Network.PhotoApiModel
import com.niden.unsplash.Network.Urls

class PhotoViewModel(apiModel: PhotoApiModel) {
    val id: String? = apiModel.id
    val urls: Urls? = apiModel.urls
}