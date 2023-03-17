package com.mustafa.tklakazan.interfaces

import android.content.Context
import android.widget.TextView

interface UserPointsITF {

    fun getUserPoints(email : String, pointText: TextView)
    fun setUserPoints(context: Context,email : String,points: Int)
    fun removePoints(context: Context,currentPoints: Int,removedPoints : Int)
}