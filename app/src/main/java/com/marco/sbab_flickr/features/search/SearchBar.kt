package com.marco.sbab_flickr.features.search

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marco.sbab_flickr.R
import com.marco.sbab_flickr.ui.theme.SBAB_FlickrTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    query: String,
    searchButtonUIState: SearchButtonUIState,
    onQueryCancelled: () -> Unit,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        SearchInputText(
            modifier = Modifier.weight(10f),
            query,
            onTextChange,
            onQueryCancelled
        )
        Spacer(modifier = Modifier.size(8.dp))
        Button(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight(),
            contentPadding = PaddingValues(0.dp),
            enabled = searchButtonUIState == SearchButtonUIState.ENABLED,
            onClick = {
                keyboardController?.hide()
                onSearchClicked(query)
            })
        {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Filled.Search,
                contentDescription = stringResource(id = R.string.search_icon),
            )

        }
    }
}

@Composable
private fun SearchInputText(
    modifier: Modifier,
    searchText: String,
    onTextChange: (String) -> Unit,
    onCancelClicked: () -> Unit
) {
    TextField(
        modifier = modifier,
        value = searchText,
        onValueChange = {
            onTextChange(it)
        },
        placeholder = {
            Text(
                text = stringResource(id = R.string.search_placeholder),
            )
        },
        textStyle = TextStyle(
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        ),
        singleLine = true,
        trailingIcon = {
            IconButton(
                onClick = {
                    if (searchText.isNotEmpty()) {
                        onTextChange("")
                        onCancelClicked()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(id = R.string.close_icon),
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    SBAB_FlickrTheme {
        SearchBar(
            query = "",
            searchButtonUIState = SearchButtonUIState.ENABLED,
            onQueryCancelled = {},
            onTextChange = {},
            onSearchClicked = {},
        )
    }
}