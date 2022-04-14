package com.grupo10.medicalthy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    val database = FirebaseAuth.getInstance()
    val authObject = Auth()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        setup()
    }

    private fun setup(){

        title = getString(R.string.signInTitle)

        loginButton.setOnClickListener {
            login()
        }
    }


    //Función login usuario con email y contraseña
    private fun login() : Boolean {

        val success = authObject.verifyCredentialsSignIn(signInEmail, signInPassword)

        if (success[0] as Boolean) {
            database.signInWithEmailAndPassword(signInEmail.text.toString(), signInPassword.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        goHome(it.result?.user?.email?: "", ProviderType.BASIC)     //Empieza la acticidad de pantalla de Inicio
                    } else {
                        showAlert(getString(R.string.authErrorMessage))
                    }
                }
        } else {
            if(success[1] == "empty"){
                showAlert(getString(R.string.emptyCredentialsErrorMessage))
            }else if(success[1] == "length"){
                showAlert(getString(R.string.lengthCredentialsErrorMessage))
            }
            return false
        }
        return true;
    }


    /*
       Funcion que nos permite mostrar el mensaje de error que le pasemos como parametro
        */
    private fun showAlert(errorMessage : String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.simpleErrorMessage))
        builder.setMessage(errorMessage)
        builder.setPositiveButton(getString(R.string.acceptMessage), null)
        val dialog : AlertDialog = builder.create()
        dialog.show()
    }


    //Función para iniciar la actividad de inicio tras registrarse o hacer logIn
    private fun goHome(email : String, provider : ProviderType){

        val homeIntent = Intent(this, HomeActivity::class.java).apply {     //Intent para iniciar la actividad de Inicio (se pasan por parámetro email + proveedor)
            putExtra(getString(R.string.intentEmail), email)
            putExtra(getString(R.string.provider), provider.name)
        }
        startActivity(homeIntent)   //Llamada a la actividad de pantalla de Inicio
    }
}