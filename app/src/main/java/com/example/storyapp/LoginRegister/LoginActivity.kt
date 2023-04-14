package com.example.storyapp.LoginRegister

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieAnimationView
import com.example.storyapp.CustomView.EmailEditText
import com.example.storyapp.CustomView.PasswordEditText
import com.example.storyapp.Main.MainActivity
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var edLoginEmail: EmailEditText
    private lateinit var edLoginPassword: PasswordEditText
    private lateinit var edLoginButton: Button
    private lateinit var edLoginToRegister: Button
    private lateinit var edLoginLoading: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = getSharedPreferences("loginPref", Context.MODE_PRIVATE)
        val loginViewModel = ViewModelProvider(this, ViewModelFactory(preferences))[LoginViewModel::class.java]

        val intentToRegister = Intent(this@LoginActivity, RegisterActivity::class.java)
        val intentToMain = Intent(this@LoginActivity, MainActivity::class.java)

        edLoginEmail = binding.edLoginEmail
        edLoginPassword = binding.edLoginPassword
        edLoginButton = binding.edLoginButton
        edLoginToRegister = binding.edLoginToRegister
        edLoginLoading = binding.loginLoading

        loginViewModel.isLoading.observe(this){
            if(it){
                edLoginLoading.visibility = View.VISIBLE
                edLoginLoading.playAnimation()
            } else {
                edLoginLoading.visibility = View.INVISIBLE
                edLoginLoading.cancelAnimation()
            }
        }

        edLoginButton.setOnClickListener {
            if(!edLoginEmail.isError() && !edLoginPassword.isError()){
                loginViewModel.getLogin(edLoginEmail.text.toString().lowercase(), edLoginPassword.text.toString())
            } else {
                Toast.makeText(this, R.string.login_error_toast, Toast.LENGTH_SHORT).show()
            }
        }

        loginViewModel.acceptance.observe(this){
            if (it) {
                startActivity(intentToMain)
            }
        }

        loginViewModel.message.observe(this){
            if (it == "Failure"){
                Toast.makeText(this, R.string.api_failure, Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        edLoginToRegister.setOnClickListener{
            startActivity(intentToRegister)
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }

}