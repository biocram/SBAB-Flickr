package com.marco.sbab_flickr.features.search

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import coil.util.DebugLogger
import com.marco.sbab_flickr.R
import com.marco.sbab_flickr.models.ui.UISearchItem
import com.marco.sbab_flickr.ui.theme.SBAB_FlickrTheme

@Composable
fun SearchResultContent(
    uiState: UIState,
    navigateToDetails: (String) -> Unit,
) {
    when (uiState) {
        UIState.LoadFailed ->
            SimpleStateText(R.string.search_error_query)

        UIState.Loading -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        is UIState.Success -> if (uiState.isEmpty()) {
            SimpleStateText(R.string.search_empty_query)
        } else {
            SearchResultList(searchItems = uiState.data.searchItems) { itemId ->
                navigateToDetails(itemId)
            }
        }

        UIState.WaitingForQuery ->
            SimpleStateText(R.string.search_no_query_entered)
    }
}

@Composable
private fun SimpleStateText(@StringRes id: Int) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(id = id))
    }
}

@Composable
fun SearchResultList(
    searchItems: List<UISearchItem>,
    onItemClicked: (id: String) -> Unit = { }
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(items = searchItems) { item ->
            SearchItemRow(item = item, onItemClicked = onItemClicked)
        }
    }
}

@Composable
fun SearchItemRow(
    item: UISearchItem,
    onItemClicked: (id: String) -> Unit = { }
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 16.dp)
            .clickable { onItemClicked(item.id) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
            ) {
                SearchItemThumbnail(item.imageUrl)
            }
            Text(
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically),
                text = item.title,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun SearchItemThumbnail(
    thumbnailUrl: String,
) {
    val imageLoader = LocalContext.current.imageLoader.newBuilder()
        .logger(DebugLogger())
        .build()

    AsyncImage(
        modifier = Modifier
            .size(100.dp)
            .padding(4.dp),
        model = ImageRequest.Builder(LocalContext.current)
            .data(thumbnailUrl)
            .crossfade(true)
            .build(),
        imageLoader = imageLoader,
//        placeholder = painterResource(R.drawable.placeholder),
        contentScale = ContentScale.Crop,
        contentDescription = "Search item thumbnail picture",
    )
}


@Preview(showBackground = true)
@Composable
fun SearchResultContentPreview() {
    SBAB_FlickrTheme {
        SearchResultContent(
            UIState.LoadFailed,
        ) { }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchItemRowPreview() {
    SBAB_FlickrTheme {
        SearchItemRow(
            item = UISearchItem("id", "image title", "www.google.com")
        ) { }
    }
}