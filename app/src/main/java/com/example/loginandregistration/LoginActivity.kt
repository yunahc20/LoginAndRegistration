package com.example.loginandregistration

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.loginandregistration.databinding.ActivityLoginBinding

class LoginActivity:AppCompatActivity() {
    companion object{
        const val EXTRA_USERNAME = "username"
        const val EXTRA_PASSWORD = "password"
        const val EXTRA_USERID = "UserID"
        const val TAG = "LoginActivity"

    }

    val startRegistrationForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result: ActivityResult ->
        if(result.resultCode == Activity.RESULT_OK){
            val intent = result.data

            binding.editTextLoginUsername.setText(intent?.getStringExtra(LoginActivity.EXTRA_USERNAME))
            binding.editTextLoginPassword.setText(intent?.getStringExtra(LoginActivity.EXTRA_PASSWORD))
        }
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Backendless.initApp(this, Constants.APP_ID, Constants.API_KEY);

        val registrationIntent = Intent(
            this,
            RegistrationActivity::class.java
        )

        val extraUsername = binding.editTextLoginUsername.text.toString()
        registrationIntent.putExtra(EXTRA_USERNAME, extraUsername)
        val extraPassword = binding.editTextLoginPassword.text.toString()
        registrationIntent.putExtra(EXTRA_PASSWORD, extraPassword)

        startRegistrationForResult.launch(registrationIntent)
        Log.d(
            TAG, "onCreate: password=${extraPassword}" +
                    "\n          username=${extraUsername}"
        )


        binding.buttonLogin.setOnClickListener {
            Backendless.UserService.login(
                binding.editTextLoginUsername.text.toString(),
                binding.editTextLoginPassword.text.toString(),
                object: AsyncCallback<BackendlessUser?>{
                    override fun handleResponse(user: BackendlessUser?) {
                        //user has not been logged in
                        Log.d(TAG, "handleResponse: ${user?.getProperty("username")}")
                       if(user != null){
                           val loanListActivity =
                               Intent(this@LoginActivity, LoanListActivity::class.java)
                           loanListActivity.putExtra(EXTRA_USERID, user.userId)
                           startActivity(loanListActivity)

                       }
                    }
                    override fun handleFault(fault: BackendlessFault){
                        // login failed, to get the error code call fault.getCode()
                        Log.d(TAG, "handleFault: ${fault?.message}")

                    }
                }
            )
        }

        binding.textViewLoginSignUp.setOnClickListener{

            val registrationIntent = Intent(this, RegistrationActivity::class.java)
            registrationIntent.putExtra(EXTRA_USERNAME, binding.editTextLoginUsername.text.toString())
            registrationIntent.putExtra(EXTRA_PASSWORD, binding.editTextLoginPassword.text.toString())

            startRegistrationForResult.launch(registrationIntent)
        }
    }

}