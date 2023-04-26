package com.example.storyapp.Main

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.API.ApiConfig
import com.example.storyapp.Model.ListStoryItem
import com.example.storyapp.Model.StoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val preferences: SharedPreferences, private val storyRepository: StoryRepository) : ViewModel() {
    companion object {
        private const val TAG = "MainViewModel"
        private const val TOKEN = "token"
    }

    private val token : String = preferences.getString(TOKEN, null).toString()
    var stories : LiveData<PagingData<ListStoryItem>>

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _listStory = MutableLiveData<List<ListStoryItem>>()
    val listStory: LiveData<List<ListStoryItem>> = _listStory
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    init{
        _listStory.value = emptyList()
        stories = getStory(token)
    }

    fun getStory(token: String): LiveData<PagingData<ListStoryItem>>{
        return storyRepository.getStories(token, 0)?.cachedIn(viewModelScope) ?: MutableLiveData(PagingData.empty())
    }

    fun getAllStory(size: Int, location: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().stories("Bearer $token", 1, size, location)
        client.enqueue(object : Callback<StoryResponse> {
            override fun onResponse(
                call: Call<StoryResponse>,
                response: Response<StoryResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _message.value = response.body()?.message
                    _listStory.value = response.body()?.listStory
                } else {
                    _message.value = response.message()
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                _isLoading.value = false
                _message.value = "Failure"
                Log.e(TAG, "onFailure: ${message.value}")
            }
        })
    }

    fun clearToken() {
        preferences.edit().clear().apply()
    }
}