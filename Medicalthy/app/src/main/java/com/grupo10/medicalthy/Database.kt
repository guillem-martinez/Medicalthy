package com.grupo10.medicalthy

import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


/*

Clase para todas las funciones que realizen consultas a la base de datos Firebase
 */
class Database {

    companion object{
        val databaseAuth = FirebaseAuth.getInstance()
    }
    /*
    Función que permite crear el usuario en la base de datos identificandolo con el email.

     */
    public fun makeUserDatabase(email : EditText, name: EditText, surname: EditText, age: EditText, cloudFirestore : FirebaseFirestore){

        cloudFirestore.collection("users").document(email.text.toString()).set(
            hashMapOf(
                "Name" to name.text.toString(),
                "Surnames" to surname.text.toString(),
                "Age" to age.text.toString()

            )
        )


    }

}