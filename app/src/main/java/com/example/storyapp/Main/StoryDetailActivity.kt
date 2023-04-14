package com.example.storyapp.Main

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.storyapp.Model.ListStoryItem
import com.example.storyapp.databinding.ActivityStoryDetailBinding

class StoryDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoryDetailBinding

    private lateinit var detailImageView: ImageView
    private lateinit var detailItemName: TextView
    private lateinit var detailItemDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailImageView = binding.detailImageView
        detailItemName = binding.detailItemName
        detailItemDescription = binding.detailItemDescription

        val story = intent.getParcelableExtra<ListStoryItem>("Story")
        Glide.with(this)
            .load(story?.photoUrl)
            .into(detailImageView)
        detailItemName.text = story?.name
        detailItemDescription.text = story?.description
    }
}