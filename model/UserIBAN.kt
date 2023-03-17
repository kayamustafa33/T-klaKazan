package com.mustafa.tklakazan.model

import com.mustafa.tklakazan.controller.UserIBANController

class UserIBAN : UserIBANController{

    var email : String = ""
    var userName : String = ""
    var iban : String = ""
    var ibanName : String = ""

    constructor(email: String,userName : String,ibanName : String,iban : String){
        this.email = email
        this.userName = userName
        this.iban = iban
        this.ibanName = ibanName
    }

    constructor()
}