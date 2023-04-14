package com.example.storyapp.Main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.example.storyapp.Model.ListStoryItem
import com.example.storyapp.databinding.ActivityStoryDetailBinding

class StoryDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoryDetailBinding
//    private lateinit var preferences: SharedPreferences
//    private lateinit var detailViewModel: MainViewModel

    private lateinit var detailImageView: ImageView
    private lateinit var detailItemName: TextView
    private lateinit var detailItemDescription: TextView
//    private lateinit var detailLoading: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        preferences = getSharedPreferences("loginPref", Context.MODE_PRIVATE)
//        detailViewModel = ViewModelProvider(this, MainViewModelFactory(preferences))[MainViewModel::class.java]

        detailImageView = binding.detailImageView
        detailItemName = binding.detailItemName
        detailItemDescription = binding.detailItemDescription

        val story = intent.getParcelableExtra<ListStoryItem>("Story")
        Glide.with(this)
            .load(story?.photoUrl)
            .into(detailImageView)
        detailItemName.text = story?.name
        detailItemDescription.text = story?.description


        /* Bagian ini apabila mau ambil dari API */
        /*
        detailLoading = binding.detailLoading
        val id = intent.getStringExtra("StoryID")
        id?.let { detailViewModel.getDetail(id) }
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
                detailLoading.playAnimation()
            } else {
                detailLoading.visibility = View.INVISIBLE
                detailLoading.cancelAnimation()
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
        */
    }
}