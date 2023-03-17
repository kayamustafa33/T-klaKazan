package com.mustafa.tklakazan.interfaces

import android.content.Context
import android.widget.ArrayAdapter
import com.mustafa.tklakazan.adapter.IBANAdapter
import com.mustafa.tklakazan.model.UserIBAN

interface UserIBANITF {

    fun createIBAN(context: Context,userIBAN : UserIBAN)
    fun getIBAN(context: Context, ibanList : ArrayList<UserIBAN>, adapter : IBANAdapter)
    fun deleteIBAN(context: Context,email : String,arrayList: ArrayList<UserIBAN>,position : Int)
    fun getAllIBANName(context: Context, arrayList: ArrayList<String>) : ArrayList<String>
}