package com.mustafa.tklakazan.interfaces

import android.content.Context
import android.widget.EditText
import android.widget.TextView
import com.mustafa.tklakazan.adapter.WithdrawalAdapter
import com.mustafa.tklakazan.model.UserPoints
import com.mustafa.tklakazan.model.UserWithdrawal

interface UserWithdrawalITF {

    fun createWithdrawal(context: Context,withdrawal: UserWithdrawal)
    fun calculatePointsToMoney(context: Context,points: Int,pointsEditText: EditText,money: EditText,textView: TextView)
    fun getWithdrawal(context: Context,arrayList: ArrayList<UserWithdrawal>,adapter: WithdrawalAdapter,noWithdrawalText : TextView)
    fun getNameToIban(context: Context,ibanName : String) : String
}