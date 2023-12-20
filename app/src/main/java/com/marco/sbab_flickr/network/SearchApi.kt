package com.marco.sbab_flickr.network

import com.marco.sbab_flickr.models.network.FlickrSearchData
import com.marco.sbab_flickr.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET(".")
    suspend fun search(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("method") method: String = Constants.FLICKR_METHOD_PARAMETER,
        @Query("format") format: String = Constants.FLICKR_FORMAT_PARAMETER,
        @Query("nojsoncallback") noJsonCallback: String = Constants.FLICKR_NOJSONCALLBACK_PARAMETER,
        @Query("text") query: String,
    ): Response<FlickrSearchData>
}