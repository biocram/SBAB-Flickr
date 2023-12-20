package com.marco.sbab_flickr.repository

import android.util.Log
import com.marco.sbab_flickr.features.search.SEARCH_TAG
import com.marco.sbab_flickr.models.network.FlickrSearchData
import com.marco.sbab_flickr.network.SearchApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchRepository @Inject constructor(private val api: SearchApi) {
    suspend fun searchContents(searchQuery: String): FlickrSearchData? {
        try {
            val response = api.search(query = searchQuery)
            if (response.isSuccessful) {
                Log.d(SEARCH_TAG, "Successful response for searchQuery = $searchQuery")
                response.body()?.let { return it } ?: Log.d(
                    SEARCH_TAG,
                    "Found empty response body = $searchQuery"
                )
            } else {
                val errorBody = response.errorBody()?.string()
                // do something with the exception here (analytics, retry, ... )
            }
        } catch (e: HttpException) {
            // do something with the exception here (analytics, retry, ... )
            Log.e(SEARCH_TAG, "HttpException ${e.message()}", e)
        } catch (e: IOException) {
            // do something with the exception here (analytics, retry, ... )
            Log.e(SEARCH_TAG, "IOException ${e.message}", e)
        } catch (e: Exception) {
            // do something with the exception here (analytics, retry, ... )
            Log.e(SEARCH_TAG, "Exception ${e.message}", e)
        }
        return null
    }
}