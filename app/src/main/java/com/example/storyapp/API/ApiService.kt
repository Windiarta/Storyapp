package com.example.storyapp.API

import com.example.storyapp.FileUploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val name: String, val email: String, val password: String)

interface ApiService {
    @POST("/v1/login")
    fun login (@Body request: LoginRequest): Call<LoginResponse>

    @POST("/v1/register")
    fun register (@Body request: RegisterRequest): Call<RegisterResponse>

    @Multipart
    @POST("/v1/stories")
    fun upload(
        @Header("Authorization") token: String,
        @Part("description") description: RequestBody,
        @Part file: MultipartBody.Part
    ): Call<FileUploadResponse>

    @GET("/v1/stories")
    fun stories(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("location") location: Int
    ): Call<StoryResponse>

    @GET("/v1/stories/{id}")
    fun detail(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<DetailResponse>


}