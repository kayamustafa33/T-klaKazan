package com.mustafa.tklakazan.profile_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mustafa.tklakazan.R
import com.mustafa.tklakazan.databinding.ActivityCreateIbanBinding
import com.mustafa.tklakazan.model.UserIBAN

class CreateIbanActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCreateIbanBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateIbanBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth = Firebase.auth
        firebaseUser = auth.currentUser!!

        binding.createIbanBackBtn.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }

    fun createIbanClicked(view : View){
        if(binding.createIbanAddressEditText.text.toString().trim() != "" && binding.createIbanNameEditText.text.toString() != "" && binding.createFullNameEditText.text.toString() != ""){
            val userIban = UserIBAN(firebaseUser.email!!,binding.createFullNameEditText.text.toString(),binding.createIbanNameEditText.text.toString(),binding.createIbanAddressEditText.text.toString())
            userIban.createIBAN(binding.root.context,userIban)
            clearAttr()
        }else{
            Toast.makeText(applicationContext, R.string.fill_in_the_required_fields, Toast.LENGTH_LONG).show()
        }
    }

    private fun clearAttr(){
        binding.createIbanAddressEditText.text.clear()
        binding.createIbanNameEditText.text.clear()
        binding.createFullNameEditText.text.clear()
    }
}