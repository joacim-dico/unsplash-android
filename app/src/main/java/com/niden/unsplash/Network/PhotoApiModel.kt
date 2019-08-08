package com.niden.unsplash.Network

import com.squareup.moshi.Json

data class PhotoApiModel (
    @Json(name = "urls") val urls: Urls,
    @Json(name = "description") val description: String
)

data class Urls(
    @Json(name ="regular") val regular: String,
    @Json(name ="small") val small: String
)