package com.grupo10.medicalthy

import android.widget.EditText

class Auth {
    /*
        Función para verificar que las credenciales sean correctas.
        Comprobaciones:
        -Parámetros >= 6 length
        -Parámetros no vacíos

        Devuelve un Array:
        Array[0] -> True/False según si són validas las credenciales.
        Array[1] -> Devuelve un string con el código de error.
         */
    public fun verifyCredentials(email : EditText, password : EditText) : Array<Any> {

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