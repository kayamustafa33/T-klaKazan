package com.mustafa.tklakazan.model

import com.mustafa.tklakazan.controller.UserPointsController

class UserPoints : UserPointsController{

     var email : String = ""
     var points : String = ""

    constructor(email : String,points : String){
        this.email = email
        this.points = points
    }

    constructor()

}