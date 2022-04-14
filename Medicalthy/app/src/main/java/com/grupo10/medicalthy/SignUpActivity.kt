package com.grupo10.medicalthy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    val databaseAuth = FirebaseAuth.getInstance()
    val authObject = Auth()
    val cloudFirestore = FirebaseFirestore.getInstance()
    val databaseFunctions = Database()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        setUp()
    }

    private fun setUp(){

        title = getString(R.string.signUpTitle)

        signUpButton.setOnClickListener {
            register()
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
    A través de el email y password pasado por parametro se registra a este usuario en la database:FirebaseAuth
    */
    private fun register() : Boolean {

        //val success = verifyCredentials(email, password)

        //val success = authObject.verifyCredentials(signUpEmail, signUpPassword)
        val success = authObject.verifyCredentialsSignUp(signUpEmail,signUpPassword,editTextName,editTextSurname,editTextAge)

        if(success[0] as Boolean){
            databaseAuth.createUserWithEmailAndPassword(signUpEmail.text.toString(),signUpPassword.text.toString()).addOnCompleteListener {
                if(it.isSuccessful){
                    //it.result?.user?.email?: "" --> null safety
                    databaseFunctions.makeUserDatabase(signUpEmail,editTextName,editTextSurname,editTextAge,cloudFirestore)
                    goHome(it.result?.user?.email?: "", ProviderType.BASIC)     //Empieza la acticidad de pantalla de Inicio
                }
                else{
                    showAlert(getString(R.string.authErrorMessage))     //Muestra un mensaje de error de autenticación
                }
            }
        }else {
            when(success[1]){
                "empty" ->  showAlert(getString(R.string.emptyCredentialsErrorMessage))
                "length"->  showAlert(getString(R.string.lengthCredentialsErrorMessage))
                "age" ->    showAlert(getString(R.string.ageCredentialsErrorMessage))

            }

            /*
            if(success[1] == "empty"){
                showAlert(getString(R.string.emptyCredentialsErrorMessage))
            }else if(success[1] == "length"){
                showAlert(getString(R.string.lengthCredentialsErrorMessage))
            }else if(success[1] =="age"){
                showAlert(getString(R.string.ageCredentialsErrorMessage))
            }
            */
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