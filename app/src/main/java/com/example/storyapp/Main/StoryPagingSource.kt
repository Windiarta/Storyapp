package com.example.storyapp.Main

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.storyapp.API.ApiService
import com.example.storyapp.Model.ListStoryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class StoryPagingSource(private val apiService: ApiService, private val token: String, private val location: Int) : PagingSource<Int, ListStoryItem>(){
    private companion object {
        private const val TAG = "StoryPagingSource"
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = withContext(Dispatchers.IO) {
                apiService.stories("Bearer $token", page, params.loadSize, location).execute().body()?.listStory
            }
            LoadResult.Page(
                data = responseData ?: emptyList(),
                prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                nextKey = if (responseData.isNullOrEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            Log.e(TAG, exception.toString())
            if (exception is SocketTimeoutException) {
                delay(5000) // 5 seconds delay
                return load(params)
            }
            return LoadResult.Error(exception)
        }
    }
}