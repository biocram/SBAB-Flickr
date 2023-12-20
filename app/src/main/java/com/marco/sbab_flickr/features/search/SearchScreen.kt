package com.marco.sbab_flickr.features.search

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.marco.sbab_flickr.ui.theme.SBAB_FlickrTheme

@Composable
internal fun SearchRoute(
    navigateToDetails: (String) -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel(),
) {
    val searchQueryState by searchViewModel.searchQueryState.collectAsStateWithLifecycle()
    val searchResultUiState by searchViewModel.searchContentState.collectAsStateWithLifecycle()
    val searchButtonUIState by searchViewModel.searchButtonUIState.collectAsStateWithLifecycle()


    LaunchedEffect(key1 = searchResultUiState) {
        Log.d(SEARCH_TAG, "SearchRoute: searchResultUiState = $searchResultUiState ")
    }
    LaunchedEffect(key1 = searchButtonUIState) {
        Log.d(SEARCH_TAG, "SearchRoute: searchButtonUIState = $searchButtonUIState ")
    }

    SearchScreen(
        navigateToDetails = navigateToDetails,
        searchQueryState = searchQueryState,
        uiState = searchResultUiState,
        searchButtonUIState = searchButtonUIState,
        onSearchQueryChanged = searchViewModel::onSearchQueryChanged,
        onSearchQueryCancelled = searchViewModel::onSearchQueryCancelled,
        onSearchQueryStarted = searchViewModel::onSearchQueryStarted
    )

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    navigateToDetails: (String) -> Unit,
    searchQueryState: String,
    uiState: UIState,
    searchButtonUIState: SearchButtonUIState,
    onSearchQueryChanged: (String) -> Unit = {},
    onSearchQueryCancelled: () -> Unit = {},
    onSearchQueryStarted: (String) -> Unit = {},
) {

    Scaffold {
        Column(modifier = Modifier.padding(8.dp)) {
            SearchBar(
                searchQueryState,
                searchButtonUIState,
                onTextChange = {
                    onSearchQueryChanged(it)
                },
                onSearchClicked = {
                    onSearchQueryStarted(it)
                },
                onQueryCancelled = {
                    onSearchQueryCancelled()
                }
            )
            SearchResultContent(uiState, navigateToDetails)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SBAB_FlickrTheme {
        SearchScreen(
            {},
            "",
            UIState.WaitingForQuery,
            SearchButtonUIState.ENABLED,
            {},
            {},
            {}
        )
    }
}