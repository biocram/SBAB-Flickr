package com.marco.sbab_flickr.repository

import com.marco.sbab_flickr.models.FlickrItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class SearchRepository {
    fun searchContents(searchQuery: String): Flow<List<FlickrItem>> {
        return emptyFlow()
    }
}