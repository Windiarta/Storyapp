package com.example.storyapp.Main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.storyapp.API.ListStoryItem
import com.example.storyapp.AddPhotoActivity
import com.example.storyapp.LoginRegister.LoginActivity
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object{
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var mainViewModel: MainViewModel

    private lateinit var mainLoading : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences("loginPref", Context.MODE_PRIVATE)
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(preferences))[MainViewModel::class.java]
        mainViewModel.getAllStory()

        mainLoading = binding.mainLoading

        mainViewModel.listStory.observe(this){
            showList(it)
        }

        mainViewModel.message.observe(this){
            if (it == "Failure"){
                Toast.makeText(this, R.string.api_failure, Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        mainViewModel.isLoading.observe(this){
            if(it){
                mainLoading.visibility = View.VISIBLE
            } else {
                mainLoading.visibility = View.INVISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                logout(this)
                true
            }
            R.id.add -> {
                val intentToAddPhoto = Intent(this@MainActivity, AddPhotoActivity::class.java)
                startActivity(intentToAddPhoto)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    private fun logout(context: Context){
        mainViewModel.clearToken()
        val intentToMain = Intent(context, LoginActivity::class.java)
        startActivity(intentToMain)
    }

    private fun showList(items: List<ListStoryItem>) {
        binding.rvStory.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) // 2 columns and vertical orientation
        val adapter = StoryAdapter(items)
        binding.rvStory.adapter = adapter
        adapter.setOnItemClickCallback(object : StoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListStoryItem) {
                showSelectedData(data)
            }
        })
    }

    private fun showSelectedData(data : ListStoryItem){
        val intentToDetail = Intent(this@MainActivity, StoryDetailActivity::class.java)
        intentToDetail.putExtra("StoryID", data.id)
        startActivity(intentToDetail)
    }

}