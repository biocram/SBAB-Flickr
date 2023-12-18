package com.marco.sbab_flickr.features.search

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.marco.sbab_flickr.R
import com.marco.sbab_flickr.ui.theme.Purple40
import com.marco.sbab_flickr.ui.theme.PurpleGrey40

@Composable
fun SearchBar(
    query: String,
    searchButtonUIState: SearchButtonUIState,
    onQueryCancelled: () -> Unit,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(all = 24.dp)
            .fillMaxWidth()
    ) {
        SearchInputText(modifier = Modifier.weight(3f), query, onTextChange, onQueryCancelled)
        Button(
            modifier = Modifier.weight(1f),
            enabled = searchButtonUIState == SearchButtonUIState.ENABLED,
            onClick = { onSearchClicked(query) })
        {
            Text(text = stringResource(id = R.string.search_icon_label))
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
                modifier = Modifier
                    .alpha(0.15f),
                text = stringResource(id = R.string.search_placeholder),
                color = PurpleGrey40
            )
        },
        textStyle = TextStyle(
            color = PurpleGrey40,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        ),
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = stringResource(id = R.string.search_icon),
                tint = Purple40
            )
        },
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
                    tint = Purple40
                )
            }
        },
//            colors = TextFieldDefaults.textFieldColors(
//                cursorColor = MaterialTheme.colors.topAppBarContentColor,
//                focusedIndicatorColor = Color.Transparent,
//                disabledIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent,
//                backgroundColor = Color.Transparent
//            )
    )
}