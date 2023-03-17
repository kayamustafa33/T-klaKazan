package com.mustafa.tklakazan.interfaces

import android.content.Context
import com.mustafa.tklakazan.model.UserStepDetails

interface UserStepDetailsITF {

    fun createStepDetails(context: Context,userStepDetails: UserStepDetails)
    fun isStepDetailsActive(context: Context,uid : String) : Boolean
}