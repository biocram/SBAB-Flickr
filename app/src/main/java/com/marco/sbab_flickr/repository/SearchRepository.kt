package com.marco.sbab_flickr.repository

import com.marco.sbab_flickr.models.FlickrItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class SearchRepository @Inject constructor() {
    suspend fun searchContents(searchQuery: String): Flow<List<FlickrItem>> {
        delay(10000)
        return emptyFlow()
    }
}