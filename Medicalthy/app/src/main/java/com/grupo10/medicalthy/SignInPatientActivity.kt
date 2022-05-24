package com.grupo10.medicalthy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_patient.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_in_patient.*
import kotlin.math.sign

class SignInPatientActivity : AppCompatActivity() {

    val GOOGLE_SIGN_IN = 10
    val database = FirebaseAuth.getInstance()
    val authObject = Auth()

    var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_patient)

        //TODO: Si se pulsa 'atrás' se cierra la aplicación en lugar de ir a la anterior Activity
        //      Ha desaparecido la flecha de retroceso

        val bundle = intent.extras
        email = bundle?.get("email").toString()

        if(email == "" || email == null || email == "null"){
            email = "usuario1@gmail.com"
        }

        setup()
    }

    private fun setup(){
        title = "Vinculación de paciente"

        button3.setOnClickListener {
            var textEmail: String = signInEmailPatient.text.toString()
            var textPassword: String = signInPasswordPatient.text.toString()

            if((textEmail != "") && (textPassword != "") && (textEmail != email)){
                val success = authObject.verifyCredentialsSignIn(textEmail, textPassword)

                if(success[0] as Boolean){
                    database.signInWithEmailAndPassword(textEmail, textPassword)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                setResponsable(textEmail)    //Quien es su responsable (email)
                                setCuidaA(textEmail)         //Encargado de cuidar a signInEmailPatient
                            } else {
                                showAlert(getString(R.string.authErrorMessage))
                            }

                            val addPatientActivity = Intent(this, AddPatientActivity::class.java).apply{
                                putExtra(getString(R.string.intentEmail), email)
                            }

                            startActivity(addPatientActivity)
                        }
                }//if(success[0] as Boolean)
            }
            else{
                showAlert("Sabemos que te quieres mucho, pero no te puedes añadir a ti mismo")
            }
        }
    }

    private fun showAlert(errorMessage : String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.simpleErrorMessage))
        builder.setMessage(errorMessage)
        builder.setPositiveButton(getString(R.string.acceptMessage), null)
        val dialog : AlertDialog = builder.create()
        dialog.show()
    }

    override fun onBackPressed() {
        android.app.AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.onBackDialogTitle_SignInPatientActivity))
            setMessage(getString(R.string.onBackDialogMessage_SignInPatientActivity))

            setPositiveButton(getString(R.string.yesMessage)) { _, _ ->
                // Si pulsan si se cierra la app, LOCURA
                super.onBackPressed()
            }

            setNegativeButton(getString(R.string.noMessage)){_, _ ->
            }

            setCancelable(true)
        }.create().show()
    }


    private fun setResponsable(email_persona_cuidada: String){
        //Quien es su responsable (email)
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(email_persona_cuidada)
            .collection("responsable")
            .document(email)
            .set(hashMapOf("exists" to "true"))
    }

    private fun setCuidaA(email_persona_cuidada: String){
        //Encargado de cuidar a signInEmailPatient
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(email)
            .collection("cuida_a")
            .document(email_persona_cuidada)
            .set(hashMapOf("exists" to "true"))
    }

}