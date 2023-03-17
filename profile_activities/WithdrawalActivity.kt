package com.mustafa.tklakazan.profile_activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.mustafa.tklakazan.R
import com.mustafa.tklakazan.adapter.WithdrawalAdapter
import com.mustafa.tklakazan.databinding.ActivityWithdrawalBinding
import com.mustafa.tklakazan.model.UserWithdrawal

class WithdrawalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWithdrawalBinding
    private lateinit var arrayList: ArrayList<UserWithdrawal>
    private lateinit var adapter: WithdrawalAdapter
    private var profileName : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWithdrawalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        profileName = intent.getStringExtra("Username")

        arrayList = ArrayList()
        binding.withdrawalRV.layoutManager = LinearLayoutManager(this@WithdrawalActivity)
        adapter = WithdrawalAdapter(arrayList)
        binding.withdrawalRV.adapter = adapter


        getData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.create_withdrawal_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.createWithdrawalMenu -> {
                val intent = Intent(this@WithdrawalActivity,CreateWithdrawalActivity::class.java)
                intent.putExtra("ProfileName",profileName)
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getData(){
        val userWithdrawal = UserWithdrawal()
        userWithdrawal.getWithdrawal(applicationContext,arrayList,adapter,binding.noHistoryText)
    }
}