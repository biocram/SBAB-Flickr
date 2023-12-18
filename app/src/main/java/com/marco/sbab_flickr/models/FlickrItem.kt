package com.marco.sbab_flickr.models

/**
 * Represents a Flickr item as returned from the search API.
 */
data class FlickrItem(val title: String, val description: String, val imageUrl: String)