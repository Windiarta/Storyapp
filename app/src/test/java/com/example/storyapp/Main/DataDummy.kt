package com.example.storyapp.Main

import com.example.storyapp.Model.ListStoryItem

object DataDummy {
    fun generateDummyStoryEntity(): List<ListStoryItem> {
        val storyList = ArrayList<ListStoryItem>()
        for (i in 0..9) {
            val news = ListStoryItem(
                "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
                "2022-01-08T06:34:18.598Z",
                "Dimas",
                "Lorem Ipsum/",
                -16.002,
                "story-FvU4u0Vp2S3PMsFg",
                -10.212
            )
            storyList.add(news)
        }
        return storyList
    }
}