package com.marco.sbab_flickr.features.search

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marco.sbab_flickr.R
import com.marco.sbab_flickr.ui.theme.Purple40
import com.marco.sbab_flickr.ui.theme.PurpleGrey40
import com.marco.sbab_flickr.ui.theme.SBAB_FlickrTheme

private const val TAG = "SEARCH"

@Composable
fun SearchScreen(navigateToDetails: (String) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        val searchTextState = remember { mutableStateOf("") }

        SearchBar(searchTextState.value,
            onTextChange = {
                searchTextState.value = it
            },
            onSearchClicked = {
                Log.d(TAG, "onSearchClicked: $it")
            }
        )
    }
}

@Composable
fun SearchBar(
    searchText: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shadowElevation = 4.dp,
        color = Color.Black
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
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
                IconButton(
                    onClick = {
                        if (searchText.isNotEmpty()) {
                            onSearchClicked(searchText)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(id = R.string.search_icon),
                        tint = Purple40
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (searchText.isNotEmpty()) {
                            onTextChange("")
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
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(searchText)
                }
            ),
//            colors = TextFieldDefaults.textFieldColors(
//                cursorColor = MaterialTheme.colors.topAppBarContentColor,
//                focusedIndicatorColor = Color.Transparent,
//                disabledIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent,
//                backgroundColor = Color.Transparent
//            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SBAB_FlickrTheme {
        SearchScreen {}
    }
}