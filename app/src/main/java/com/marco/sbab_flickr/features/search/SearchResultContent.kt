package com.marco.sbab_flickr.features.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.marco.sbab_flickr.R
import com.marco.sbab_flickr.ui.theme.SBAB_FlickrTheme

@Composable
fun SearchResultContent(
    searchResultUIState: SearchResultUiState,
) {
    when (searchResultUIState) {
        SearchResultUiState.LoadFailed -> Text(text = stringResource(id = R.string.search_error_query))
        SearchResultUiState.Loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        is SearchResultUiState.Success -> Text(text = "SUCCESS!!!")
        SearchResultUiState.WaitingForQuery -> Text(text = stringResource(id = R.string.search_no_query_entered))
    }
}

@Preview(showBackground = true)
@Composable
fun SearchResultContentPreview() {
    SBAB_FlickrTheme {
        SearchResultContent(
            SearchResultUiState.Loading,
        )
    }
}