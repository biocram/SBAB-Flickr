package com.marco.sbab_flickr.models


/**
 * Represents the result of a user query.
 */
data class SearchResult(val items: List<SearchResultItem>)

/**
 * Represents a search query item that is ready to be presented on the screen.
 */
data class SearchResultItem(val title: String, val description: String, val imageUrl: String)