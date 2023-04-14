package com.example.storyapp.Main

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.API.*
import com.example.storyapp.Model.ListStoryItem
import com.example.storyapp.Model.StoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel (private val preferences: SharedPreferences): ViewModel() {
    companion object{
        private const val TAG = "MainViewModel"
        private const val TOKEN = "token"
    }
    private var token: String = preferences.getString(TOKEN, null).toString()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _listStory = MutableLiveData<List<ListStoryItem>>()
    val listStory: LiveData<List<ListStoryItem>> = _listStory
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
//    private val _detailStory = MutableLiveData<Story>()
//    val detailStory: LiveData<Story> = _detailStory

    fun getAllStory(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().stories("Bearer $token",1, 100, 0)
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

    fun clearToken(){
        preferences.edit().clear().apply()
    }

    /*
    fun getDetail(id: String){
        _isLoading.value = true

        val client = ApiConfig.getApiService().detail("Bearer $token", id)
        Log.e(TAG, client.toString())
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _message.value = response.body()?.message
                    _detailStory.value = response.body()?.story
                } else {
                    _message.value = response.message()
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                _message.value = "Failure"
                Log.e(TAG, "onFailure: ${message.value}")
            }
        })
    }
    */
}