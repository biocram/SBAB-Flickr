package com.marco.sbab_flickr.features.search

import com.marco.sbab_flickr.models.SearchResult

sealed interface SearchResultUiState {
    data object WaitingForQuery : SearchResultUiState

    data object Loading : SearchResultUiState

    data object LoadFailed : SearchResultUiState

    data class Success(
        val items: SearchResult = SearchResult(emptyList()),
    ) : SearchResultUiState
}

enum class SearchButtonUIState {
    ENABLED, DISABLED
}