package com.mustafa.tklakazan.bottom_fragments

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import com.google.android.gms.ads.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mustafa.tklakazan.model.User
import com.mustafa.tklakazan.model.UserPoints
import com.mustafa.tklakazan.databinding.FragmentHomeBinding
import com.mustafa.tklakazan.R
import com.mustafa.tklakazan.model.CheckInternet

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private var mRewardedAd: RewardedAd? = null
    private var currentAmount : Int? = null
    private var printAmount : Int? = null
    private lateinit var userPoints: UserPoints
    private var progressDialog : ProgressDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)

        val checkInternet = CheckInternet()
        checkInternet.isOnline(binding.root.context)

        auth = FirebaseAuth.getInstance()
        firebaseUser = auth.currentUser!!

        userPoints = UserPoints()
        userPoints.getUserPoints(firebaseUser.uid,binding.pointsText)

        clickedCardView()
        //App id -> ca-app-pub-3547612698000344~3893515530
        //Rewarded Ad -> ca-app-pub-3547612698000344/2656896425
        //Rewarded Ad2 -> ca-app-pub-3547612698000344/9279778977
        //Rewarded Ad3 -> ca-app-pub-3547612698000344/6653615631
        //Rewarded Ad4-> ca-app-pub-3547612698000344/9986086481
        //Rewarded Ad5-> ca-app-pub-3547612698000344/1401288954
        //Banner Ad -> ca-app-pub-3547612698000344/8406698936
        //Withdrawal Ad -> ca-app-pub-3547612698000344/9997878550

        MobileAds.initialize(binding.root.context)

        showBannerAd()
        menuImage()

        return binding.root
    }

    private fun showRewardedAd(){
        if(mRewardedAd != null){
            mRewardedAd!!.fullScreenContentCallback = object : FullScreenContentCallback(){

                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    mRewardedAd = null
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    super.onAdFailedToShowFullScreenContent(p0)
                    mRewardedAd = null
                }

            }
            mRewardedAd!!.show(requireActivity(), OnUserEarnedRewardListener { amount ->
                currentAmount = Integer.parseInt(binding.pointsText.text.toString())
                printAmount = amount.amount + currentAmount!!
                userPoints = UserPoints()
                userPoints.setUserPoints(binding.root.context,firebaseUser.email!!,printAmount!!)
                userPoints.getUserPoints(firebaseUser.email!!,binding.pointsText)
                setupAnim()
             })
        }
    }

    private fun clickedCardView(){
        binding.watchAdCardView.setOnClickListener {
            progressDialog = ProgressDialog(binding.root.context)
            progressDialog?.setTitle(R.string.please_wait)
            progressDialog?.setMessage(getString(R.string.ad_loading))
            progressDialog?.setCanceledOnTouchOutside(false)
            progressDialog?.show()

            loadAd()
        }
    }

    private fun menuImage(){
        binding.menuImageView.setOnClickListener {
            val popupMenu = PopupMenu(binding.root.context,binding.menuImageView)
            popupMenu.menuInflater.inflate(R.menu.main_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.logout_menu -> {
                        val user = User()
                        user.logOutUser(binding.root.context)
                        activity?.finish()
                    }
                }
                true
            }
            popupMenu.show()
        }
    }

    private fun setupAnim() {

        binding.animationView.visibility = View.VISIBLE
        binding.animationView.setAnimation(R.raw.coin)
        binding.animationView.repeatCount = 10
        binding.animationView.playAnimation()

    }

    private fun showBannerAd() {
        val adRequest = AdRequest.Builder().build()
        binding.bannerAdView.loadAd(adRequest)
    }

    private fun loadAd(){
        val adRequest = AdRequest.Builder().build()

        RewardedAd.load(binding.root.context,"ca-app-pub-3547612698000344/2656896425", adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {

                mRewardedAd = null

                RewardedAd.load(binding.root.context,"ca-app-pub-3547612698000344/9279778977", adRequest, object : RewardedAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {

                        mRewardedAd = null

                        RewardedAd.load(binding.root.context,"ca-app-pub-3547612698000344/6653615631", adRequest, object : RewardedAdLoadCallback() {
                            override fun onAdFailedToLoad(adError: LoadAdError) {

                                mRewardedAd = null

                                RewardedAd.load(binding.root.context,"ca-app-pub-3547612698000344/9986086481", adRequest, object : RewardedAdLoadCallback() {
                                    override fun onAdFailedToLoad(adError: LoadAdError) {

                                        mRewardedAd = null

                                        RewardedAd.load(binding.root.context,"ca-app-pub-3547612698000344/1401288954", adRequest, object : RewardedAdLoadCallback() {
                                            override fun onAdFailedToLoad(adError: LoadAdError) {

                                                mRewardedAd = null
                                                progressDialog?.dismiss()
                                                Toast.makeText(binding.root.context,R.string.try_again_after_30_min,Toast.LENGTH_LONG).show()
                                            }

                                            override fun onAdLoaded(rewardedAd: RewardedAd) {
                                                mRewardedAd = rewardedAd
                                                progressDialog?.dismiss()
                                                showRewardedAd()
                                            }

                                        })
                                    }

                                    override fun onAdLoaded(rewardedAd: RewardedAd) {
                                        mRewardedAd = rewardedAd
                                        progressDialog?.dismiss()
                                        showRewardedAd()
                                    }

                                })
                            }

                            override fun onAdLoaded(rewardedAd: RewardedAd) {
                                mRewardedAd = rewardedAd
                                progressDialog?.dismiss()
                                showRewardedAd()
                            }

                        })
                    }

                    override fun onAdLoaded(rewardedAd: RewardedAd) {
                        mRewardedAd = rewardedAd
                        progressDialog?.dismiss()
                        showRewardedAd()
                    }

                })
            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                mRewardedAd = rewardedAd
                progressDialog?.dismiss()
                showRewardedAd()
            }
        })
    }
}
