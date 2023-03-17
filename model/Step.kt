package com.mustafa.tklakazan.model

import com.mustafa.tklakazan.controller.StepController

class Step : StepController {

    private var email : String = ""
    private var mondaySteps = ""
    private var tuesdaySteps = ""
    private var wednesdaySteps = ""
    private var thursdaySteps = ""
    private var fridaySteps = ""
    private var saturdaySteps = ""
    private var sundaySteps = ""

    constructor(email : String,
                mondaySteps : String,
                tuesdaySteps : String,
                wednesdaySteps : String,
                thursdaySteps : String,
                fridaySteps : String,
                saturdaySteps : String,
                sundaySteps : String){
        this.email = email
        this.mondaySteps = mondaySteps
        this.tuesdaySteps = tuesdaySteps
        this.wednesdaySteps = wednesdaySteps
        this.thursdaySteps = thursdaySteps
        this.fridaySteps = fridaySteps
        this.saturdaySteps = saturdaySteps
        this.sundaySteps = sundaySteps
    }

    constructor()

}