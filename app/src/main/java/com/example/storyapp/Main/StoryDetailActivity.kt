package com.example.storyapp.Main

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityStoryDetailBinding

class StoryDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoryDetailBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var detailViewModel: MainViewModel

    private lateinit var detailImageView: ImageView
    private lateinit var detailItemName: TextView
    private lateinit var detailItemDescription: TextView
    private lateinit var detailLoading: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences("loginPref", Context.MODE_PRIVATE)
        detailViewModel = ViewModelProvider(this, MainViewModelFactory(preferences))[MainViewModel::class.java]

        val id = intent.getStringExtra("StoryID")

        id?.let { detailViewModel.getDetail(it) }

        detailImageView = binding.detailImageView
        detailItemName = binding.detailItemName
        detailItemDescription = binding.detailItemDescription
        detailLoading = binding.detailLoading

        detailViewModel.detailStory.observe(this){
            Glide.with(this)
                .load(it.photoUrl)
                .into(detailImageView)
            detailItemName.text = it.name
            detailItemDescription.text = it.description
        }

        detailViewModel.isLoading.observe(this){
            if(it){
                detailLoading.visibility = View.VISIBLE
            } else {
                detailLoading.visibility = View.INVISIBLE
            }
        }

        detailViewModel.message.observe(this){
            if (it == "Failure"){
                Toast.makeText(this, R.string.api_failure, Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}