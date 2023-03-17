package com.mustafa.tklakazan.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mustafa.tklakazan.R
import com.mustafa.tklakazan.databinding.ActivityForgotPasswordBinding
import com.mustafa.tklakazan.model.User

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding : ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.reset_password)

    }

    fun sendEmailClicked(view : View){
        val email = binding.userEmailEditText.text.toString().trim()

        if(email != ""){
            val changePassword = User()
            changePassword.forgotPassword(binding.root.context,email)
            binding.userEmailEditText.text.clear()
        }else{
            Toast.makeText(this@ForgotPasswordActivity,getString(R.string.enter_your_email_address),Toast.LENGTH_SHORT).show()
        }
    }
}