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
    public fun verifyCredentialsSignUp(email : String, password : String, name : String, surname : String, age: String) : Array<Any> {

        val arrayResult = arrayOf<Any>(true,"")

        if(email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty() && surname.isNotEmpty() && age.isNotEmpty()){

            var age = age.toInt()

            if(password.length >= 6){


                if(age in 1..120)
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


    public fun verifyCredentialsSignIn(email : String, password : String) : Array<Any> {

        val arrayResult = arrayOf<Any>(true,"")

        if(email.isNotEmpty() && password.isNotEmpty()){

            if(password.length >= 6){

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