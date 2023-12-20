package com.marco.sbab_flickr.models.network



data class FlickrSearchData(
    val photos: Photos
)

data class Photos (
    val page: Long,
    val pages: Long,
    val perpage: Long,
    val total: Long,
    val photo: List<Photo>
)

data class Photo (
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: Long,
    val title: String,
    val ispublic: Long,
    val isfriend: Long,
    val isfamily: Long
)