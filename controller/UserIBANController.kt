package com.mustafa.tklakazan.controller

import android.content.Context
import android.content.Intent
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.mustafa.tklakazan.interfaces.UserIBANITF
import com.mustafa.tklakazan.model.UserIBAN
import com.mustafa.tklakazan.R
import com.mustafa.tklakazan.adapter.IBANAdapter
import com.mustafa.tklakazan.view.MainActivity
import java.util.UUID

open class UserIBANController : UserIBANITF {

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun createIBAN(context: Context,userIBAN: UserIBAN) {
        initFirebase()

        val uuid = UUID.randomUUID()
        databaseReference.child(firebaseUser.uid).child(uuid.toString()).setValue(userIBAN).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(context,"Created.",Toast.LENGTH_LONG).show()
                context.startActivity(Intent(context,MainActivity::class.java))
            }else{
                Toast.makeText(context, R.string.fail,Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener {
            Toast.makeText(context,R.string.fail,Toast.LENGTH_LONG).show()
        }

    }

    override fun getIBAN(context: Context, ibanList: ArrayList<UserIBAN>, adapter: IBANAdapter) {
        initFirebase()

        val ibanListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(dataSnapshot in snapshot.children){
                        val email = dataSnapshot.child("email").value.toString()
                        val iban = dataSnapshot.child("iban").value.toString()
                        val ibanName = dataSnapshot.child("ibanName").value.toString()
                        val userName = dataSnapshot.child("userName").value.toString()

                        val userIBAN = UserIBAN(email,userName,ibanName,iban)
                        ibanList.add(userIBAN)
                    }

                    adapter.notifyDataSetChanged()

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,R.string.fail,Toast.LENGTH_LONG).show()
            }

        }
        databaseReference.child(firebaseUser.uid).addValueEventListener(ibanListener)
    }

    override fun deleteIBAN(context: Context,email: String,arrayList: ArrayList<UserIBAN>,position : Int) {
        initFirebase()
        val query : Query = databaseReference.child(firebaseUser.uid).orderByChild("iban").equalTo(arrayList[position].iban)

        query.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                Toast.makeText(context,"Deleted",Toast.LENGTH_LONG).show()
                context.startActivity(Intent(context,MainActivity::class.java))
                for(dataSnapshot in snapshot.children){
                    dataSnapshot.ref.removeValue()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    override fun getAllIBANName(context: Context, arrayList: ArrayList<String>) : ArrayList<String> {
        initFirebase()

        var ibanName : String?
        val listener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(dataSnapshot in snapshot.children){
                        ibanName = dataSnapshot.child("ibanName").value.toString()
                        if(ibanName != ""){
                            arrayList.add(ibanName!!)
                        }
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }

        databaseReference.child(firebaseUser.uid).addValueEventListener(listener)

        return arrayList
    }

    private fun initFirebase(){
        auth = Firebase.auth
        firebaseUser = auth.currentUser!!
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("IBAN")
    }

}