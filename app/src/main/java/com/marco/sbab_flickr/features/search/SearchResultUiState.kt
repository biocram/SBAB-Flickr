package com.marco.sbab_flickr.features.search

import com.marco.sbab_flickr.models.FlickrItem

sealed interface SearchResultUiState {
    data object Loading : SearchResultUiState

    /**
     * The state query is empty or too short to trigger a search.
     */
    data object QueryTooShort : SearchResultUiState

    data object LoadFailed : SearchResultUiState

    data class Success(
        val items: List<FlickrItem> = emptyList(),
    ) : SearchResultUiState
}