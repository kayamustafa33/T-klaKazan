package com.mustafa.tklakazan.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mustafa.tklakazan.controller.UserController
import com.mustafa.tklakazan.model.User
import com.mustafa.tklakazan.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySignUpBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

    }

    fun signUpClicked(view : View){

        val email : String = binding.userEmailEditText.text.toString().trim()
        val password : String = binding.userPasswordEditText.text.toString().trim()
        val userName : String = binding.usernameEditText.text.toString()
        val userSurname : String = binding.userSurnameEditText.text.toString()
        val confirmPassword : String = binding.userConfirmPasswordEditText.text.toString().trim()


        if(email != "" && password != "" && userName != "" && userSurname != "" && confirmPassword != ""){
            if(password == confirmPassword){
                clearAttr()
                val user = User(userName,userSurname,email,password)
                user.createUserDatabase(applicationContext,email,password,userName,userSurname)
            }else {
                Toast.makeText(applicationContext,"Passwords doesn't match!",Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(applicationContext,"Fill in the required fields!",Toast.LENGTH_LONG).show()
        }

    }

    fun singInTextClicked(view : View){
        val intent = Intent(this@SignUpActivity,SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    private fun clearAttr(){
        binding.usernameEditText.text.clear()
        binding.userSurnameEditText.text.clear()
        binding.userEmailEditText.text.clear()
        binding.userPasswordEditText.text.clear()
        binding.userConfirmPasswordEditText.text.clear()

    }
}