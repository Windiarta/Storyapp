package com.example.storyapp.LoginRegister

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.API.ApiConfig
import com.example.storyapp.API.LoginRequest
import com.example.storyapp.API.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel (private val preferences: SharedPreferences): ViewModel(){
    companion object{
        private const val TAG = "LoginViewModel"
        private const val TOKEN = "token"
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _acceptance = MutableLiveData<Boolean>()
    val acceptance: LiveData<Boolean> = _acceptance
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message


    init {
        _acceptance.value = false
        if(preferences.getString(TOKEN, null) != null){
            if(preferences.getString(TOKEN, null)!!.isNotEmpty()){
                _acceptance.value = true
            }
        }
    }

    fun getLogin(email: String, password: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().login(LoginRequest(email, password))
        Log.e(TAG, client.toString())
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    if(response.body()?.message.equals("success")){
                        _message.value = response.body()?.message
                        _acceptance.value = true
                        val editor = preferences.edit()
                        editor.putString(TOKEN, response.body()?.loginResult?.token)
                        editor.apply()
                    }
                } else {
                    _message.value = response.message()
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                _message.value = "Failure"
                Log.e(TAG, "onFailure: ${message.value}")
            }
        })
    }
}