package com.example.loginandregistration

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.loginandregistration.databinding.ActivityLoginBinding
import com.example.loginandregistration.databinding.ActivityRegistrationBinding
import com.mistershorr.loginandregistration.RegistrationUtil
import com.mistershorr.loginandregistration.RegistrationUtil.validatePassword
import com.mistershorr.loginandregistration.RegistrationUtil.validateUser
import com.mistershorr.loginandregistration.RegistrationUtil.validateUser

class RegistrationActivity: AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding

    companion object{
        val TAG = "RegistrationActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //retrieve any info from the intent
        val username = intent.getStringExtra(LoginActivity.EXTRA_USERNAME) ?: ""
        val password = intent.getStringExtra(LoginActivity.EXTRA_PASSWORD) ?: ""

        binding.editTextRegistrationUsername?.setText(username)
        binding.editTextPassword?.setText(password)

        binding.buttonRegistrationRegister.setOnClickListener {
            val password = binding.editTextPassword?.text.toString()
            val confirm = binding.editTextConfirmPassword?.text.toString()
            val username = binding.editTextRegistrationUsername?.text.toString()
            val name = binding.editTextTextPersonName?.text.toString()
            val email = binding.editTextRegistrationEmail?.text.toString()

            if (validatePassword(password, confirm) &&
                RegistrationUtil.validateUser(username)) {

                registrationUserOnBackendless(username, password, name, email)
            }
        }
    }

    private fun registrationUserOnBackendless(
        username: String,
        password: String,
        name: String,
        email: String) {
        val user = BackendlessUser();
        user.setProperty("email", binding.editTextRegistrationEmail.text.toString())
        user.password = binding.editTextPassword?.text.toString()
        user.setProperty("name", binding.editTextTextPersonName?.text.toString())
        user.setProperty("username", binding.editTextRegistrationUsername?.text.toString())

        Backendless.UserService.register(user, object: AsyncCallback<BackendlessUser?>{
            override fun handleResponse(registerUser: BackendlessUser?){
                Log.d(TAG, "handleResponse: ${user.getProperty("username")} successfully registered")
                val resultIntent = Intent().apply{
                    putExtra(
                        LoginActivity.EXTRA_USERNAME,
                        binding.editTextRegistrationUsername?.text.toString()
                    )
                    putExtra(LoginActivity.EXTRA_PASSWORD, password)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }

            override fun handleFault(fault: BackendlessFault?) {
                Log.d(TAG, "handleFault: ${fault?.message}")
            }
        })
    }
}

