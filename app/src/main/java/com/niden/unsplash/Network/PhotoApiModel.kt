package com.niden.unsplash.Network

import com.niden.unsplash.PhotosGrid.Urls
import com.squareup.moshi.Json

data class PhotoApiModel (
    @field:Json(name ="id") val id: String,
    @field:Json(name ="urls") val urls: Urls
)