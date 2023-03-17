package com.mustafa.tklakazan.controller

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.mustafa.tklakazan.R
import com.mustafa.tklakazan.adapter.WithdrawalAdapter
import com.mustafa.tklakazan.interfaces.UserWithdrawalITF
import com.mustafa.tklakazan.model.UserWithdrawal
import com.mustafa.tklakazan.view.MainActivity
import java.util.Random
import java.util.UUID

open class WithdrawalController : UserWithdrawalITF {

    private lateinit var auth: FirebaseAuth
    private lateinit var reference : DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var firebaseUser: FirebaseUser

    override fun createWithdrawal(context: Context, withdrawal: UserWithdrawal) {
        initFirebase()
        //create new withdrawal
        val uuid = UUID.randomUUID()
        reference.child(firebaseUser.uid).child(uuid.toString()).setValue(withdrawal).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(context,R.string.successful,Toast.LENGTH_LONG).show()
                context.startActivity(Intent(context,MainActivity::class.java))
            }else{
                Toast.makeText(context,R.string.fail,Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener {
            Toast.makeText(context,R.string.fail,Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun calculatePointsToMoney(context: Context, points: Int,pointsEditText: EditText, money: EditText, textView: TextView) {
        if(points < 1000.0){
            textView.visibility = View.VISIBLE
            textView.text = context.getString(R.string.must_1000_points)
        }else{
            textView.visibility = View.GONE
        }

        if(pointsEditText.text.toString() != ""){
            val calculate : Double = ((Integer.parseInt(pointsEditText.text.toString()) / 1000.0) * 1.0)
            money.setText("${String.format("%.2f",calculate)} ₺")
        }

    }

    override fun getWithdrawal(context: Context, arrayList: ArrayList<UserWithdrawal>, adapter: WithdrawalAdapter,noWithdrawalText : TextView) {
        initFirebase()
        //get all withdrawal

        val listener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(dataSnapshot in snapshot.children){
                        val email = dataSnapshot.child("email").value.toString()
                        val username = dataSnapshot.child("username").value.toString()
                        val iban = dataSnapshot.child("iban").value.toString()
                        val ibanName = dataSnapshot.child("ibanName").value.toString()
                        val usedPoints = dataSnapshot.child("usedPoints").value.toString()
                        val money = dataSnapshot.child("money").value.toString()
                        val status = dataSnapshot.child("status").value.toString()

                        val userWithdrawal = UserWithdrawal(email,username,iban,ibanName,usedPoints,money,status)
                        arrayList.add(userWithdrawal)
                    }
                    adapter.notifyDataSetChanged()
                }

                if(arrayList.isEmpty()){
                    noWithdrawalText.visibility = View.VISIBLE
                }else{
                    noWithdrawalText.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,R.string.fail,Toast.LENGTH_LONG).show()
            }

        }

        reference.child(firebaseUser.uid).addValueEventListener(listener)
    }

    override fun getNameToIban(context: Context, ibanName: String): String {
        initFirebase()

        var ibanAddress = ""
        val ibanListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(dataSnapshot in snapshot.children){
                        if(firebaseUser.email == dataSnapshot.child("Email").value.toString()){
                            if(ibanName == dataSnapshot.child("ibanName").value.toString()){
                                ibanAddress = dataSnapshot.child("iban").value.toString()
                            }
                        }

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }

        reference.addValueEventListener(ibanListener)

        return ibanAddress
    }

    private fun initFirebase(){
        auth = FirebaseAuth.getInstance()
        firebaseUser = auth.currentUser!!
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("Withdrawal")
    }

}