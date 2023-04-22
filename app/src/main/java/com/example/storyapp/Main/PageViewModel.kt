package com.example.storyapp.Main

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.API.ApiConfig
import com.example.storyapp.Model.ListStoryItem

class PageViewModel (storyRepository: StoryRepository, preferences: SharedPreferences) : ViewModel() {
    companion object {
        private const val TOKEN = "token"
    }
    private var token: String = preferences.getString(TOKEN, null).toString()
    val stories: LiveData<PagingData<ListStoryItem>> =
        storyRepository.getStories(token, 1).cachedIn(viewModelScope)
}


class ViewModelFactory(private val context: Context, private val preferences: SharedPreferences) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PageViewModel(Injection.provideRepository(context), preferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val apiService = ApiConfig.getApiService()
        return StoryRepository(apiService)
    }
}
