package com.example.loginandregistration

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.loginandregistration.databinding.ActivityLoginBinding
import com.example.loginandregistration.databinding.ActivityRegistrationBinding

class RegistrationActivity: AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //retrieve any info from the intent
        val username = intent.getStringExtra(LoginActivity.EXTRA_USERNAME) ?: ""
        val password = intent.getStringExtra(LoginActivity.EXTRA_PASSWORD) ?: ""

        binding.editTextRegistrationUsername?.setText(username)
        binding.editTextPassword?.setText(password)
    }

}