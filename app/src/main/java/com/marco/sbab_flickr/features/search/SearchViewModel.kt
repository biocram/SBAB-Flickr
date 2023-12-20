package com.marco.sbab_flickr.features.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marco.sbab_flickr.usecases.GetSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val SEARCH_TAG = "SEARCH"

/** Minimum length for the search query to trigger a network search */
private const val SEARCH_QUERY_MIN_LENGTH = 2

@HiltViewModel
class SearchViewModel @Inject constructor(private val getSearchUseCase: GetSearchUseCase) :
    ViewModel() {

    private var _searchQuery = MutableStateFlow("")
    val searchQueryState = _searchQuery.asStateFlow()

    private val _searchButtonUIState = MutableStateFlow(SearchButtonUIState.DISABLED)
    val searchButtonUIState = _searchButtonUIState.asStateFlow()

    private val _searchContentState =
        MutableStateFlow<UIState>(UIState.WaitingForQuery)
    val searchContentState: StateFlow<UIState> = _searchContentState.asStateFlow()

    fun onSearchQueryChanged(query: String) {
        Log.d(SEARCH_TAG, "onSearchQueryChanged(query = $query)")
        //TODO: use emit or .value? emit requires "suspend"
//        _searchQuery.emit(value)
        _searchQuery.value = query
        _searchButtonUIState.value =
            if (_searchQuery.value.isQueryValid() && _searchContentState.value != UIState.Loading) {
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
        _searchContentState.value = UIState.Loading
        _searchButtonUIState.value = SearchButtonUIState.DISABLED

        viewModelScope.launch {
            getSearchUseCase(query)?.let {
                Log.d(SEARCH_TAG, "Got ${it.searchItems.size} search items from network call")
                _searchContentState.value = UIState.Success(it)
                if (_searchQuery.value.isQueryValid()) {
                    _searchButtonUIState.value = SearchButtonUIState.ENABLED
                }
            } ?: emitSearchError()
        }
    }

    private fun emitSearchError() {
        Log.d(SEARCH_TAG, "emitSearchError()")
        _searchContentState.value = UIState.LoadFailed
        if (_searchQuery.value.isQueryValid()) {
            _searchButtonUIState.value = SearchButtonUIState.ENABLED
        }
    }

    private fun String.isQueryValid() = this.length > SEARCH_QUERY_MIN_LENGTH
}