package com.niden.unsplash.Network

import com.squareup.moshi.Json

data class SearchResultModel(@Json(name ="total") val total: Int,
                             @field:Json(name ="total_pages") val totalPages: Int,
                             @Json(name ="results") val results: List<PhotoApiModel>)