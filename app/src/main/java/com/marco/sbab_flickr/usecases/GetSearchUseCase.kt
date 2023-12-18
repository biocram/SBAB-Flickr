package com.marco.sbab_flickr.usecases

import com.marco.sbab_flickr.models.FlickrItem
import com.marco.sbab_flickr.models.SearchResult
import com.marco.sbab_flickr.models.SearchResultItem
import com.marco.sbab_flickr.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetSearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {

    suspend operator fun invoke(
        searchQuery: String
    ): Flow<SearchResult> =
        try {
            searchRepository.searchContents(searchQuery).mapToSearchResults()
        } catch (e: Exception) {
            // do something with the exception here (analytics, retry, ... ) and then throw it
            throw e
        }
}

private fun Flow<List<FlickrItem>>.mapToSearchResults(): Flow<SearchResult> {
    return flowOf(SearchResult(listOf()))
}
