package com.example.storyapp.LoginRegister

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.CustomView.CustomEditText
import com.example.storyapp.CustomView.EmailEditText
import com.example.storyapp.CustomView.PasswordEditText
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var edRegisterName: CustomEditText
    private lateinit var edRegisterEmail: EmailEditText
    private lateinit var edRegisterPassword: PasswordEditText
    private lateinit var edRegisterButton: Button
    private lateinit var edRegisterToLogin: Button
    private lateinit var edRegisterLoading: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val registerViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)

        edRegisterName = binding.edRegisterName
        edRegisterEmail = binding.edRegisterEmail
        edRegisterPassword = binding.edRegisterPassword
        edRegisterButton = binding.edRegisterButton
        edRegisterToLogin = binding.edRegisterToLogin
        edRegisterLoading = binding.registerLoading

        edRegisterToLogin.setOnClickListener {
            startActivity(intent)
        }

        registerViewModel.isLoading.observe(this){
            if(it){
                edRegisterLoading.visibility = View.VISIBLE
            } else {
                edRegisterLoading.visibility = View.INVISIBLE
            }
        }

        edRegisterButton.setOnClickListener {
            if(!edRegisterName.isError() && !edRegisterEmail.isError() && !edRegisterPassword.isError()){
                registerViewModel.getRegister(edRegisterName.text.toString(), edRegisterEmail.text.toString().lowercase(), edRegisterPassword.text.toString())
            } else {
                Toast.makeText(this, R.string.login_error_toast, Toast.LENGTH_SHORT).show()
            }
        }

        registerViewModel.acceptance.observe(this){
            if (it) {
                startActivity(intent)
            }
        }

        registerViewModel.message.observe(this){
            if (it == "Failure"){
                Toast.makeText(this, R.string.api_failure, Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}