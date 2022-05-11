package com.grupo10.medicalthy

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.Theme_Medicalthy)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        setup()
        //verifyActiveSession()
    }

    override fun onStart() {
        super.onStart()

        startLayout.visibility = View.VISIBLE //En caso de hacer logOut o se pierda la sesión se vuelve a mostrar
    }

    private fun verifyActiveSession(){
        val preferences = getSharedPreferences(getString(R.string.preferencesFile), Context.MODE_PRIVATE)
        val email = preferences?.getString(getString(R.string.intentEmail), null) //null valor por defecto
        val provider = preferences?.getString(getString(R.string.provider), null)

        if(email != null && provider != null){
            startLayout.visibility = View.INVISIBLE //Si hay sesión iniciada no muestra el layout
            goHome(email, ProviderType.valueOf(provider))
        }
    }

    private fun setup() {

        title = getString(R.string.app_name)

        goSignIn.setOnClickListener{
            goToSignIn()
        }

        goSignUp.setOnClickListener{
            goToSignUp()
        }
    }

    private fun goToSignIn(){
        val signInIntent = Intent(this, SignInActivity::class.java)
        startActivity(signInIntent)
    }

    private fun goToSignUp(){
        val signUpIntent = Intent(this, SignUpActivity::class.java)
        startActivity(signUpIntent)
    }

    private fun goHome(email : String, provider : ProviderType){

        val homeIntent = Intent(this, HomeActivity::class.java).apply {     //Intent para iniciar la actividad de Inicio (se pasan por parámetro email + proveedor)
            putExtra(getString(R.string.intentEmail), email)
            putExtra(getString(R.string.provider), provider.name)
        }
        startActivity(homeIntent)   //Llamada a la actividad de pantalla de Inicio
    }
}