package com.mustafa.tklakazan.controller

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.parser.IntegerParser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.mustafa.tklakazan.interfaces.UserPointsITF
import com.mustafa.tklakazan.R
import java.time.LocalDate

open class UserPointsController : UserPointsITF {

    private lateinit var auth: FirebaseAuth
    private lateinit var reference : DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var firebaseUser: FirebaseUser

    override fun getUserPoints(email: String,pointText: TextView) {
        initFirebase()

        val userPointsListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){

                    val firebaseUserEmail : String = snapshot.child("userEmail").value.toString()
                    val userPoints : String = snapshot.child("points").value.toString()

                    if(firebaseUserEmail == firebaseUser.email){
                        pointText.text = userPoints
                    }

                }else{
                    pointText.text = "0"
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }

        reference.addValueEventListener(userPointsListener)



    }

    @SuppressLint("NewApi")
    override fun setUserPoints(context: Context, email: String, points: Int) {
        initFirebase()
        val current = LocalDate.now()
        var data : HashMap<String, Any> = HashMap()
        data["userEmail"] = firebaseUser.email!!
        data["points"] = points
        data["date"] = current.toString()

        reference.setValue(data).addOnCompleteListener {
            if(it.isSuccessful){
                //Animation of gold.
            }
        }.addOnFailureListener {
            Toast.makeText(context,R.string.fail, Toast.LENGTH_LONG).show()
        }


    }

    @SuppressLint("NewApi")
    override fun removePoints(context: Context, currentPoints: Int,removedPoints : Int) {
        initFirebase()
        val current = LocalDate.now()
        var data : HashMap<String, Any> = HashMap()
        data["userEmail"] = firebaseUser.email!!
        data["points"] = currentPoints - removedPoints
        data["date"] = current.toString()

        reference.setValue(data).addOnCompleteListener {
            if(it.isSuccessful){
                //Animation of gold.
            }
        }.addOnFailureListener {
            Toast.makeText(context,R.string.fail, Toast.LENGTH_LONG).show()
        }
    }

    private fun initFirebase(){
        auth = FirebaseAuth.getInstance()
        firebaseUser = auth.currentUser!!
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("UserPoints").child(firebaseUser.uid)
    }

}