package com.niden.unsplash.PhotosGrid

class PhotosGridViewModel(
    val currentPage: Int = 0,
    val results: Int = 0,
    val totalPages: Int = 0,
    var currentQuery: String = "",
    var photos: List<PhotoViewModel>? = null)
