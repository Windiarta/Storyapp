package com.example.storyapp.Main

import android.content.Context
import com.example.storyapp.API.ApiConfig

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val apiService = ApiConfig.getApiService()
        return StoryRepository(apiService)
    }
}
