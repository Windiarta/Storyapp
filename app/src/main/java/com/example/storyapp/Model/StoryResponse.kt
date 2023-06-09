package com.example.storyapp.Model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class StoryResponse(
    val listStory: List<ListStoryItem>,
    val error: Boolean,
    val message: String
)

@Parcelize
data class ListStoryItem(
	val photoUrl: String,
	val createdAt: String,
	val name: String,
	val description: String,
	val lon: Double,
	val id: String,
	val lat: Double
) : Parcelable

