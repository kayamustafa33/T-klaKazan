package com.mustafa.tklakazan.interfaces

import android.content.Context
import android.widget.TextView

interface UserITF {
    fun logOutUser(context : Context)

    fun createUserDatabase(context: Context, email : String, Password : String, Fname : String, Lname : String)
    fun signInUser(context: Context,Email: String,Password: String)
    fun getUserData(context: Context,email: String,fullNameText : TextView?)
    fun forgotPassword(context: Context,email : String)
    fun getUserName(context: Context) : String
}