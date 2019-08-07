package com.niden.unsplash.Network

import com.squareup.moshi.Json

data class PhotoApiModel (
    @field:Json(name ="id") val id: String,
    @field:Json(name ="urls") val urls: Urls
)

data class Urls(
    @field:Json(name ="regular") val regular: String,
    @field:Json(name ="small")val small: String
)