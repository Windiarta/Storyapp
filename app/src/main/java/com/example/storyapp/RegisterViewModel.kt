package com.example.storyapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel(){
    companion object{
        private const val TAG = "RegisterViewModel"
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _acceptance = MutableLiveData<Boolean>()
    val acceptance: LiveData<Boolean> = _acceptance
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    init {
        _acceptance.value = false
    }

    fun getRegister(name: String, email: String, password: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().register(RegisterRequest(name, email, password))
        Log.e(TAG, client.toString())
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    if(response.body()?.message == "User Created"){
                        _message.value = response.body()?.message
                        _acceptance.value = true
                    }
                } else {
                    _message.value = response.message()
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                _message.value = "Failure"
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}