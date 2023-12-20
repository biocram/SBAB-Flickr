package com.marco.sbab_flickr.features.search

import com.marco.sbab_flickr.models.ui.UISearchData

sealed interface UIState {
    data object WaitingForQuery : UIState

    data object Loading : UIState

    data object LoadFailed : UIState

    data class Success(
        val data: UISearchData = UISearchData(emptyList()),
    ) : UIState {
        fun isEmpty() = data.searchItems.isEmpty()
    }
}

enum class SearchButtonUIState {
    ENABLED, DISABLED
}