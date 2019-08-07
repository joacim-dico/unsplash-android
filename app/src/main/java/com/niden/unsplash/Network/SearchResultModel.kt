package com.niden.unsplash.Network

import com.squareup.moshi.Json

data class SearchResultModel(@field:Json(name ="total") val total: Int,
                             @field:Json(name ="total_pages") val totalPages: Int,
                             @field:Json(name ="results") val results: List<PhotoApiModel>)