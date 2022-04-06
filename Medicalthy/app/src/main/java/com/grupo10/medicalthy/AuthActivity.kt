package com.grupo10.medicalthy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
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


            register(email,password,database)

            /*
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
            */

        }
    }


    /*
    Funcion que nos permite mostrar el mensaje de error que le pasemos como parametro
     */
    private fun showAlert(errorMessage:String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(errorMessage)
        builder.setPositiveButton("Aceptar", null)
        val dialog : AlertDialog = builder.create()
        dialog.show()
    }


    /*
    A través de el email y password pasado por parametro se registra a este usuario en la database:FirebaseAuth
     */

    private fun register(email:EditText, password:EditText, database:FirebaseAuth): Boolean {

        if(email.text.isNotEmpty() && password.text.isNotEmpty()){


            database.createUserWithEmailAndPassword(email.text.toString(),password.text.toString()).addOnCompleteListener {

                if(it.isSuccessful){
                    R.layout.activity_home
                }
                else{
                    showAlert("Se ha producido un error autenticando al usuario.")
                }
            }
        }else
        {
            showAlert("Correo i/o electrónico vacios, porfavor ingrese el texto restante.")
        }


        return true;
    }
}