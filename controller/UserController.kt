package com.mustafa.tklakazan.controller

import android.content.Context
import android.content.Intent
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.mustafa.tklakazan.interfaces.UserITF
import com.mustafa.tklakazan.R
import com.mustafa.tklakazan.view.MainActivity
import com.mustafa.tklakazan.view.SignInActivity
import com.mustafa.tklakazan.view.SplashScreenActivity

open class UserController : UserITF{

    private lateinit var auth: FirebaseAuth
    private lateinit var reference : DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var firebaseUser: FirebaseUser

    override fun logOutUser(context: Context) {
        auth = Firebase.auth
        firebaseUser = auth.currentUser!!
        auth.signOut()
        val intent = Intent(context, SplashScreenActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        context.startActivity(intent)
    }

    override fun createUserDatabase(context: Context, email : String, Password : String, Fname : String, Lname : String) {

        initFirebase()

        auth.createUserWithEmailAndPassword(email,Password).addOnCompleteListener {
            if(it.isSuccessful){
                firebaseUser = auth.currentUser!!
                val userData : HashMap<String, Any> = HashMap()
                userData["First Name"] = Fname
                userData["Last Name"] = Lname
                userData["Email"] = email
                userData["Password"] = Password

                reference.child(firebaseUser.uid).setValue(userData).addOnCompleteListener {
                    auth.signOut()
                    val intent = Intent(context,SignInActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                }.addOnFailureListener {
                    Toast.makeText(context,R.string.fail, Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    override fun signInUser(context: Context, Email: String, Password: String) {

        auth = Firebase.auth

        auth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener {
            if(it.isSuccessful){
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)
            }else{
                Toast.makeText(context, R.string.invalid_user,Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun getUserData(context: Context, email: String,fullNameText : TextView?){
        initFirebase()
        var firstName = ""
        var lastName = ""
        var fullName = ""

        val userDataListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(dataSnapshot in snapshot.children){
                        firstName = dataSnapshot.child("First Name").value.toString()
                        lastName = dataSnapshot.child("Last Name").value.toString()

                        if(email == dataSnapshot.child("Email").value.toString()){
                            fullName = "$firstName $lastName"
                            fullNameText?.text = fullName
                        }

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,R.string.fail,Toast.LENGTH_LONG).show()
            }

        }

        reference.addValueEventListener(userDataListener)
    }

    override fun forgotPassword(context: Context, email: String) {
        initFirebase()

        Firebase.auth.sendPasswordResetEmail(email).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(context,"Email Sent.",Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun getUserName(context: Context) : String{
        initFirebase()
        firebaseUser = auth.currentUser!!
        var username : String
        var userSurname : String
        var fullName = ""
        val nameListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for(dataSnapshot in snapshot.children){
                        username = dataSnapshot.child("First Name").value.toString()
                        userSurname = dataSnapshot.child("Last Name").value.toString()

                        if(firebaseUser.email == dataSnapshot.child("Email").value.toString()){
                            fullName = "$username $userSurname"
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        }

        reference.addValueEventListener(nameListener)

        return fullName
    }

    private fun initFirebase(){
        auth = Firebase.auth
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("User")
    }


}