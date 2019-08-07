package com.niden.unsplash.PhotosGrid

import android.net.Uri
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class PhotoViewModel {

    @Json(name = "id")
    val id: String? = null

    @Json(name = "urls")
    val urls: Urls? = null
}

class Urls {
    val regular: String? = null
    val small: String? = null

}