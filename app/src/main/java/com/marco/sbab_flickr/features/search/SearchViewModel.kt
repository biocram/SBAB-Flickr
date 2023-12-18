package com.marco.sbab_flickr.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marco.sbab_flickr.usecases.GetSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/** Minimum length for the search query to trigger a network search */
private const val SEARCH_QUERY_MIN_LENGTH = 2

@HiltViewModel
class SearchViewModel(private val getSearchUseCase: GetSearchUseCase) : ViewModel() {

    private val _searchButtonUIState = MutableStateFlow(SearchButtonUIState.DISABLED)
    val searchButtonUIState = _searchButtonUIState

    private val _searchResultUiState =
        MutableStateFlow<SearchResultUiState>(SearchResultUiState.WaitingForQuery)
    val searchResultUiState: StateFlow<SearchResultUiState> = _searchResultUiState

    fun onSearchQueryChanged(query: String) {
        _searchButtonUIState.value =
            if (query.length > SEARCH_QUERY_MIN_LENGTH) SearchButtonUIState.ENABLED else SearchButtonUIState.DISABLED
        _searchResultUiState.value = SearchResultUiState.WaitingForQuery
    }

    fun onSearchQueryCancelled() {
        _searchResultUiState.value = SearchResultUiState.WaitingForQuery
        _searchButtonUIState.value = SearchButtonUIState.DISABLED
    }

    fun onSearchQueryStarted(query: String) {
        _searchResultUiState.value = SearchResultUiState.Loading
        _searchButtonUIState.value = SearchButtonUIState.DISABLED
        try {
            viewModelScope.launch {
                getSearchUseCase(query).collect {
                    _searchResultUiState.value = SearchResultUiState.Success(it)
                    _searchButtonUIState.value = SearchButtonUIState.ENABLED
                }
            }
        } catch (e: Exception) {
            _searchResultUiState.value = SearchResultUiState.LoadFailed
            _searchButtonUIState.value = SearchButtonUIState.ENABLED
        }
    }
}