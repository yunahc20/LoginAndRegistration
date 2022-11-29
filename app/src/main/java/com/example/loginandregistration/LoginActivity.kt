package com.example.loginandregistration

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.loginandregistration.databinding.ActivityLoginBinding

class LoginActivity:AppCompatActivity() {
    companion object{
        val EXTRA_USERNAME = "username"
        val EXTRA_PASSWORD = "password"
    }
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textViewLoginSignUp.setOnClickListener{

            val registrationIntent = Intent(this, RegistrationActivity::class.java)
            registrationIntent.putExtra(EXTRA_USERNAME, binding.editTextLoginUsername.text.toString())
            registrationIntent.putExtra(EXTRA_PASSWORD, binding.editTextLoginPassword.text.toString())

            startActivity(registrationIntent)
        }
    }
}