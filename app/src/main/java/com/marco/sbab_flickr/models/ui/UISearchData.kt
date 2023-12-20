package com.marco.sbab_flickr.models.ui


/**
 * Represents the result of a user query.
 */
data class UISearchData(val searchItems: List<UISearchItem>)

/**
 * Represents a search query item that is ready to be presented on the screen.
 */
data class UISearchItem(val id: String, val title: String, val imageUrl: String)