package com.mustafa.tklakazan.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth
import com.mustafa.tklakazan.bottom_fragments.HomeFragment
import com.mustafa.tklakazan.bottom_fragments.ProfileFragment
import com.mustafa.tklakazan.R
import com.mustafa.tklakazan.bottom_fragments.StepFragment
import com.mustafa.tklakazan.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        supportActionBar?.hide()


        auth = FirebaseAuth.getInstance()
        MobileAds.initialize(this)
        replaceFragment(HomeFragment())

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.homeMenu -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.profileMenu -> {
                    replaceFragment(ProfileFragment())
                    true
                }
                R.id.stepsMenu -> {
                    replaceFragment(StepFragment())
                    true
                }
                else -> {
                    true
                }
            }
        }

    }

    fun replaceFragment(fragment : Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainActivityFrameLayout,fragment)
        transaction.commit()
    }
}