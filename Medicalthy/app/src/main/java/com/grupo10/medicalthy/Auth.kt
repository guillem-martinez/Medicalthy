package com.grupo10.medicalthy

import android.widget.EditText

class Auth {


    fun verifyCredentials(email : EditText, password : EditText) : Array<Any> {

        val arrayResult = arrayOf<Any>(true,"")


        if(email.text.isNotEmpty() && password.text.isNotEmpty()){

            if(password.text.length >= 6){

                return arrayResult
            }
            else{
                arrayResult[0] = false
                arrayResult[1] = "length"
                return arrayResult
            }

        }
        else{
            arrayResult[0] = false
            arrayResult[1] = "empty"
            return arrayResult
        }
    }



}