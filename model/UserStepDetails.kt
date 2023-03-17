package com.mustafa.tklakazan.model

import com.mustafa.tklakazan.controller.UserStepDetailsController

class UserStepDetails : UserStepDetailsController {

    private var email : String = ""
    private var height : String = ""
    private var weight : String = ""
    private var dailyGoals : String = ""


    constructor(email : String,height : String,weight : String,dailyGoals : String){
        this.email = email
        this.height = height
        this.weight = weight
        this.dailyGoals = dailyGoals
    }

    constructor()
}