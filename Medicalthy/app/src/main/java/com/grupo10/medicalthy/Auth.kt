package com.grupo10.medicalthy

import android.widget.EditText

class Auth {
    /*
        Función para verificar que las credenciales sean correctas.
        Comprobaciones en el siguiente orden:
        -Parámetros no vacíos
        -Parámetro password >= 6 length
        -Parámetro age entre los valores 1-120


        Devuelve un Array:
        Array[0] -> True/False según si són validas las credenciales.
        Array[1] -> Devuelve un string con el código de error.
         */
    public fun verifyCredentialsSignUp(email : EditText, password : EditText,name:EditText,surname : EditText, age: EditText) : Array<Any> {

        val arrayResult = arrayOf<Any>(true,"")

        if(email.text.isNotEmpty() && password.text.isNotEmpty() && name.text.isNotEmpty() && surname.text.isNotEmpty() && age.text.isNotEmpty()){

            if(password.text.length >= 6){


                if(age.text.toString().toInt() in 1..120)
                {
                    return arrayResult

                }
                else{
                    arrayResult[0] = false
                    arrayResult[1] = "age"
                    return arrayResult

                }

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


    public fun verifyCredentialsSignIn(email : EditText, password : EditText) : Array<Any> {

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