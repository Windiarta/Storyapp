package com.example.storyapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.storyapp.API.ApiService
import com.example.storyapp.Model.ListStoryItem

class StoryRepository (private val apiService: ApiService) {
    fun getStories(token: String, location: Int): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, token, location)
            }
        ).liveData
    }
}