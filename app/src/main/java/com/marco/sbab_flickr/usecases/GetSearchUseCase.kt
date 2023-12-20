package com.marco.sbab_flickr.usecases

import com.marco.sbab_flickr.models.network.FlickrSearchData
import com.marco.sbab_flickr.models.network.Photo
import com.marco.sbab_flickr.models.ui.UISearchData
import com.marco.sbab_flickr.models.ui.UISearchItem
import com.marco.sbab_flickr.repository.SearchRepository
import com.marco.sbab_flickr.util.Constants
import javax.inject.Inject

class GetSearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {

    suspend operator fun invoke(
        searchQuery: String
    ): UISearchData? =
        searchRepository.searchContents(searchQuery)?.mapToUiModel()
}

private fun FlickrSearchData.mapToUiModel(): UISearchData {
    val searchItems = this.photos.photo.map {
        UISearchItem(
            it.id,
            it.title,
            generateFlickrUrl(it.server, it.id, it.secret, Constants.FLICKR_THUMBNAIL_150)
        )
    }
    return UISearchData(searchItems)
}

fun generateFlickrUrl(serverId: String, id: String, secret: String, sizeSuffix: String): String {
    return Constants.FLICKR_IMAGE_URL.format(serverId, id, secret, sizeSuffix)
}
