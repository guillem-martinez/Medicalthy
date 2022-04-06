package com.grupo10.medicalthy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {

    val database = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        setUp()
    }

    private fun setUp(){

        title = getString(R.string.authTitle)

        signUpButton.setOnClickListener {
            if(email.text.isNotEmpty() && password.text.isNotEmpty()){
                database.createUserWithEmailAndPassword(email.text.toString(),password.text.toString()).addOnCompleteListener {

                    if(it.isSuccessful){
                        R.layout.activity_home
                    }
                    else{
                        showAlert()
                    }
                }
            }
        }
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario.")
        builder.setPositiveButton("Aceptar", null)
        val dialog : AlertDialog = builder.create()
        dialog.show()
    }
}