package com.mustafa.tklakazan.model

import com.mustafa.tklakazan.controller.UserController

class User : UserController{

    private var username : String = ""
    private var userSurname : String = ""
    private var userEmail : String = ""
    private var userPassword : String = ""

    constructor(Fname: String, Lname: String, Email: String, Password: String){
        this.username = Fname
        this.userSurname = Lname
        this.userEmail = Email
        this.userPassword = Password
    }

    constructor()

}