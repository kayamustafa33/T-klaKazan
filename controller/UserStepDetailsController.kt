package com.mustafa.tklakazan.controller

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.mustafa.tklakazan.R
import com.mustafa.tklakazan.interfaces.UserStepDetailsITF
import com.mustafa.tklakazan.model.UserStepDetails

open class UserStepDetailsController : UserStepDetailsITF {

    private lateinit var auth: FirebaseAuth
    private lateinit var reference : DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var firebaseUser: FirebaseUser

    override fun createStepDetails(context: Context,userStepDetails: UserStepDetails) {
        initFirebase()

        reference.child(firebaseUser.uid).setValue(userStepDetails).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(context,"Created.",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context,context.getString(R.string.fail),Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun isStepDetailsActive(context: Context, uid: String): Boolean {

        //Aktif olarak bir stepDetails var ise problem yok
        //Eğer yoksa kullanıcıya her zaman bir layout göster

        return false
    }

    private fun initFirebase(){
        auth = Firebase.auth
        firebaseUser = auth.currentUser!!
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("StepDetails")
    }

}