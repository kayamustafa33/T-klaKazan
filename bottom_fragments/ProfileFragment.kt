package com.mustafa.tklakazan.bottom_fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.ads.AdRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mustafa.tklakazan.controller.UserController
import com.mustafa.tklakazan.profile_activities.MyIBAN
import com.mustafa.tklakazan.profile_activities.WithdrawalActivity
import com.mustafa.tklakazan.databinding.FragmentProfileBinding
import com.mustafa.tklakazan.profile_activities.AboutActivity


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProfileBinding.inflate(inflater,container,false)

        auth = FirebaseAuth.getInstance()
        firebaseUser = auth.currentUser!!

        //Banner Ad Id -> ca-app-pub-3547612698000344/8569015301

        val user = UserController()
        user.getUserData(binding.root.context,firebaseUser.email!!,binding.usernameProfileTextView)

        binding.userEmailProfileTextView.text = firebaseUser.email
        goToOtherActivity()
        showBannerAd()
        return binding.root
    }

    private fun goToOtherActivity(){
        binding.myIbanCardView.setOnClickListener {
            startActivity(Intent(binding.root.context,MyIBAN::class.java))
        }

        binding.withdrawalCardView.setOnClickListener {
            val intent = Intent(binding.root.context,WithdrawalActivity::class.java)
            intent.putExtra("Username",binding.usernameProfileTextView.text.toString())
            startActivity(intent)
        }

        binding.aboutCardView.setOnClickListener {
            startActivity(Intent(binding.root.context, AboutActivity::class.java))
        }
    }

    private fun showBannerAd() {
        val adRequest = AdRequest.Builder().build()
        binding.bannerAdView.loadAd(adRequest)
    }

}