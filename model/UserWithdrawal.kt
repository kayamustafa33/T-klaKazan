package com.mustafa.tklakazan.model

import com.mustafa.tklakazan.controller.WithdrawalController

class UserWithdrawal : WithdrawalController {

    var email : String = ""
    var username : String = ""
    var iban : String = ""
    var ibanName : String = ""
    var usedPoints : String = ""
    var money : String = ""
    var status : String = ""

    constructor(email : String,username : String,iban : String, ibanName : String,usedPoints : String,money : String,status : String){
        this.email = email
        this.username = username
        this.iban = iban
        this.ibanName = ibanName
        this.usedPoints = usedPoints
        this.money = money
        this.status = status
    }
    constructor()
}