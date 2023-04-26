package com.example.storyapp.Main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.example.storyapp.LoginRegister.LoginActivity
import com.example.storyapp.Photo.AddPhotoActivity
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var mainViewModel: MainViewModel

    private lateinit var mainLoading: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainLoading = binding.mainLoading

        preferences = getSharedPreferences("loginPref", Context.MODE_PRIVATE)
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(preferences, this))[MainViewModel::class.java]

        showList()

        mainViewModel.isLoading.observe(this){
            if(it){
                mainLoading.visibility = View.VISIBLE
                mainLoading.playAnimation()
            } else {
                mainLoading.visibility = View.INVISIBLE
                mainLoading.cancelAnimation()
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
            R.id.maps -> {
                val intentToMaps = Intent(this@MainActivity, MapsActivity::class.java)
                startActivity(intentToMaps)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    private fun logout(context: Context) {
        mainViewModel.clearToken()
        val intentToMain = Intent(context, LoginActivity::class.java)
        startActivity(intentToMain)
    }

    private fun showList() {
        binding.rvStory.layoutManager = LinearLayoutManager(this)
        val adapter = DataAdapter()
        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        mainViewModel.stories.observe(this) {
            adapter.submitData(lifecycle, it)
            Log.e(TAG, it.toString())
        }
    }
}