package com.grupo10.medicalthy

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home_cuidador.*
import kotlinx.android.synthetic.main.activity_home_patient.*

class HomeActivityPatient: AppCompatActivity() {
    var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_patient)

        setup()
    }

    private fun setup(){
        title = getString(R.string.homeTitle)

        //Recuperar extras del intent de SignIn/SignUp Activity
        val bundle = intent.extras
        email = bundle?.get(getString(R.string.intentEmail)).toString()
        val provider = bundle?.get(getString(R.string.provider))

        if(email == null || email == "null" || email == ""){
            email = "usuario1@gmail.com"
        }

        btnShowShotsPatient.setOnClickListener {
            goToShowSots(email)
        }

    }


    private fun goToShowSots(email : String){
        val showShotsInIntent = Intent(this, ShowShotsActivity::class.java).apply{
            putExtra(getString(R.string.intentEmail), email)
        }
        startActivity(showShotsInIntent)
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.onBackDialogTitle))
            setMessage(getString(R.string.onBackDialogMessage))

            setPositiveButton(getString(R.string.yesMessage)) { _, _ ->
                // Si pulsan si se cierra la app, LOCURA
                finishAffinity()
            }

            setNegativeButton(getString(R.string.noMessage)){_, _ ->
            }

            setCancelable(true)
        }.create().show()
    }
}