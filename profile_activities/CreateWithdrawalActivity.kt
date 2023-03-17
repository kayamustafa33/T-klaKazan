package com.mustafa.tklakazan.profile_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Toast
import android.widget.Toolbar
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mustafa.tklakazan.R
import com.mustafa.tklakazan.controller.UserIBANController
import com.mustafa.tklakazan.controller.UserPointsController
import com.mustafa.tklakazan.databinding.ActivityCreateWithdrawalBinding
import com.mustafa.tklakazan.model.User
import com.mustafa.tklakazan.model.UserPoints
import com.mustafa.tklakazan.model.UserWithdrawal

class CreateWithdrawalActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCreateWithdrawalBinding
    private lateinit var arrayList: ArrayList<String>
    private lateinit var adapter: ArrayAdapter<String>
    private var ibanName : String? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private var profileName : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateWithdrawalBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        supportActionBar?.hide()

        profileName = intent.getStringExtra("ProfileName")

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        auth = Firebase.auth
        firebaseUser = auth.currentUser!!

        arrayList = ArrayList()

        val myIban = UserIBANController()
        arrayList = myIban.getAllIBANName(this@CreateWithdrawalActivity,arrayList)
        adapter = ArrayAdapter(this@CreateWithdrawalActivity, R.layout.list_item,arrayList)
        binding.autoCompleteTextView.setAdapter(adapter)

        binding.autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            ibanName =parent.getItemAtPosition(position).toString()
        }

        val userPoints = UserPoints()
        userPoints.getUserPoints(firebaseUser.email!!,binding.totalPointsText)

        binding.writePointEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                calculateMoney()
            }

            override fun afterTextChanged(s: Editable?) {

            }


        })

    }

    fun createWithdrawalClicked(view : View){

        if(binding.writePointEditText.text.toString() != ""){
            if(Integer.parseInt(binding.writePointEditText.text.toString()) < 100000){
                Toast.makeText(applicationContext,getString(R.string.it_must_be_least_100_tl),Toast.LENGTH_SHORT).show()
            }else{
                if(Integer.parseInt(binding.writePointEditText.text.toString()) <= Integer.parseInt(binding.totalPointsText.text.toString())){
                    if(ibanName != "" && binding.writePointEditText.text.toString() != ""){
                        val user = User()
                        val getIbanAddress = UserWithdrawal()
                        val withdrawal = UserWithdrawal(firebaseUser.email!!,profileName!!,getIbanAddress.getNameToIban(applicationContext,ibanName!!),ibanName!!,binding.writePointEditText.text.toString(),binding.pointToMoneyEditText.text.toString(),binding.root.context.getString(R.string.pending))
                        withdrawal.createWithdrawal(this@CreateWithdrawalActivity,withdrawal)
                        val points = UserPoints()
                        points.removePoints(this@CreateWithdrawalActivity,Integer.parseInt(binding.totalPointsText.text.toString()),Integer.parseInt(binding.writePointEditText.text.toString()))
                        clearAttr()
                    }else{
                        Toast.makeText(applicationContext,getString(R.string.you_must_choose_iban),Toast.LENGTH_LONG).show()
                    }
                }else{
                    Toast.makeText(applicationContext,"You can withdraw up to ${binding.totalPointsText.text} points!",Toast.LENGTH_LONG).show()
                }
            }
        }


    }

    private fun calculateMoney(){
        val userWithdrawal = UserWithdrawal()
        userWithdrawal.calculatePointsToMoney(applicationContext,Integer.parseInt(binding.totalPointsText.text.toString()),binding.writePointEditText,binding.pointToMoneyEditText,binding.alertMessage)
    }


    private fun clearAttr(){
        binding.writePointEditText.text.clear()
        binding.pointToMoneyEditText.text.clear()
    }
}