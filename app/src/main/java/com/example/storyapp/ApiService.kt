package com.example.storyapp

import retrofit2.Call
import retrofit2.http.*

data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val name: String, val email: String, val password: String)

interface ApiService {
    @POST("/v1/login")
    fun login (@Body request: LoginRequest): Call<LoginResponse>

    @POST("/v1/register")
    fun register (@Body request: RegisterRequest): Call<RegisterResponse>
}