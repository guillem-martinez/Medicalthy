package com.grupo10.medicalthy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInPatientActivity : AppCompatActivity() {

    val GOOGLE_SIGN_IN = 10
    val database = FirebaseAuth.getInstance()
    val authObject = Auth()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_patient)

        //TODO: Si se pulsa 'atrás' se cierra la aplicación en lugar de ir a la anterior Activity
        //      Ha desaparecido la flecha de retroceso

        setup()
    }

    private fun setup(){

        title = getString(R.string.signInTitle)

    }
}