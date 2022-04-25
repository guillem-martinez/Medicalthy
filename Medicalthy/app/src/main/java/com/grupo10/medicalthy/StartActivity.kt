package com.grupo10.medicalthy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        setup()
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
}