package com.marco.sbab_flickr.features.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marco.sbab_flickr.usecases.GetSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val SEARCH_TAG = "SEARCH"

/** Minimum length for the search query to trigger a network search */
private const val SEARCH_QUERY_MIN_LENGTH = 2

@HiltViewModel
class SearchViewModel @Inject constructor(private val getSearchUseCase: GetSearchUseCase) :
    ViewModel() {

    private var _searchQuery = MutableStateFlow<String>("")
    val searchQueryState = _searchQuery

    private val _searchButtonUIState = MutableStateFlow(SearchButtonUIState.DISABLED)
    val searchButtonUIState = _searchButtonUIState

    private val _searchResultUiState =
        MutableStateFlow<SearchResultUiState>(SearchResultUiState.WaitingForQuery)
    val searchResultUiState: StateFlow<SearchResultUiState> = _searchResultUiState

    fun onSearchQueryChanged(query: String) {
        Log.d(SEARCH_TAG, "onSearchQueryChanged(query = $query)")
        _searchQuery.value = query
        _searchButtonUIState.value =
            if (_searchQuery.value.isQueryValid() && _searchResultUiState.value != SearchResultUiState.Loading) {
                SearchButtonUIState.ENABLED
            } else SearchButtonUIState.DISABLED
    }

    fun onSearchQueryCancelled() {
        Log.d(SEARCH_TAG, "onSearchQueryCancelled()")
        _searchQuery.value = ""
        _searchButtonUIState.value = SearchButtonUIState.DISABLED
    }

    fun onSearchQueryStarted(query: String) {
        Log.d(SEARCH_TAG, "onSearchQueryStarted(query = $query)")
        _searchResultUiState.value = SearchResultUiState.Loading
        _searchButtonUIState.value = SearchButtonUIState.DISABLED
        try {
            viewModelScope.launch {
                getSearchUseCase(query).collect {
                    _searchResultUiState.value = SearchResultUiState.Success(it)
                    if (_searchQuery.value.isQueryValid()) {
                        _searchButtonUIState.value = SearchButtonUIState.ENABLED
                    }
                }
            }
        } catch (e: Exception) {
            _searchResultUiState.value = SearchResultUiState.LoadFailed
            if (_searchQuery.value.isQueryValid()) {
                _searchButtonUIState.value = SearchButtonUIState.ENABLED
            }
        }
    }

    private fun String.isQueryValid() = this.length > SEARCH_QUERY_MIN_LENGTH
}