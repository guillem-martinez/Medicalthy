package com.grupo10.medicalthy

import android.content.Intent
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
            register()
        }

        loginButton.setOnClickListener {
            login()
        }
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


    /*
    Función para verificar que las credenciales sean correctas.
    Comprobaciones:
    -Parámetros >= 6 length
    -Parámetros no vacíos

    Devuelve un Array:
    Array[0] -> True/False según si són validas las credenciales.
    Array[1] -> Devuelve un string con el código de error.
     */
    private fun verifyCredentials(email : EditText, password : EditText) : Array<Any> {

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

    /*
    A través de el email y password pasado por parametro se registra a este usuario en la database:FirebaseAuth
    */
    private fun register() : Boolean {

        val success = verifyCredentials(email, password)

        if(success[0] as Boolean){
            database.createUserWithEmailAndPassword(email.text.toString(),password.text.toString()).addOnCompleteListener {
                if(it.isSuccessful){
                    //it.result?.user?.email?: "" --> null safety
                    goHome(it.result?.user?.email?: "", ProviderType.BASIC)     //Empieza la acticidad de pantalla de Inicio
                }
                else{
                    showAlert(getString(R.string.authErrorMessage))     //Muestra un mensaje de error de autenticación
                }
            }
        }else {
            if(success[1] == "empty"){
                showAlert(getString(R.string.emptyCredentialsErrorMessage))
            }else if(success[1] == "length"){
                showAlert(getString(R.string.lengthCredentialsErrorMessage))
            }

            return false
        }
        return true;
    }

    //Función login usuario con email y contraseña
    private fun login() : Boolean {

        val success = verifyCredentials(email, password)

        if (success[0] as Boolean) {
            database.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
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


    //Función para iniciar la actividad de inicio tras registrarse o hacer logIn
    private fun goHome(email : String, provider : ProviderType){

        val homeIntent = Intent(this, HomeActivity::class.java).apply {     //Intent para iniciar la actividad de Inicio (se pasan por parámetro email + proveedor)
            putExtra(getString(R.string.intentEmail), email)
            putExtra(getString(R.string.provider), provider.name)
        }
        startActivity(homeIntent)   //Llamada a la actividad de pantalla de Inicio
    }
}