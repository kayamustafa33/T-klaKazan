package com.mustafa.tklakazan.profile_activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mustafa.tklakazan.model.UserIBAN
import com.mustafa.tklakazan.R
import com.mustafa.tklakazan.adapter.IBANAdapter
import com.mustafa.tklakazan.databinding.ActivityMyIbanBinding

class MyIBAN : AppCompatActivity() {

    private lateinit var binding : ActivityMyIbanBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var arrayList: ArrayList<UserIBAN>
    private lateinit var adapter : IBANAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyIbanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //Set the activity title
        supportActionBar?.title = "IBAN"
        auth = Firebase.auth
        firebaseUser = auth.currentUser!!

        arrayList = ArrayList()

        binding.myIbanRV.layoutManager = LinearLayoutManager(this)
        adapter = IBANAdapter(arrayList)
        binding.myIbanRV.adapter = adapter

        getData()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //Initialized the menu
        menuInflater.inflate(R.menu.create_iban_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.createIbanMenu -> {
                createIbanIcon()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun createIbanIcon(){
        startActivity(Intent(this@MyIBAN,CreateIbanActivity::class.java))
    }

    private fun getData(){
        val userIban = UserIBAN()
        userIban.getIBAN(this@MyIBAN,arrayList, adapter)
    }
}