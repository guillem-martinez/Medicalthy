package com.grupo10.medicalthy

import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_auth.*

class Auth {


    public fun login(email:String ,password:String,database:FirebaseAuth){
        if(email.isNotEmpty()  && password.isNotEmpty()){
            database.createUserWithEmailAndPassword(email,password).addOnCompleteListener {

                if(it.isSuccessful){
                    R.layout.activity_home
                }
                else{
                    showAlert()
                }
            }
        }




    }

    public fun showAlert(){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario.")
        builder.setPositiveButton("Aceptar", null)
        val dialog : AlertDialog = builder.create()
        dialog.show()



    }

}