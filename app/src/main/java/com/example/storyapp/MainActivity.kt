package com.example.storyapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.storyapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object{
        private const val TOKEN = "token"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences("loginPref", Context.MODE_PRIVATE)
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
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    fun logout(context: Context){
        preferences.edit().clear().apply()
        val intentToMain = Intent(context, LoginActivity::class.java)
        startActivity(intentToMain)
    }
}