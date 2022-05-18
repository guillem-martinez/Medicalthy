package com.grupo10.medicalthy

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_home_cuidador.*
//import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home_hor.*
import kotlinx.android.synthetic.main.activity_home_hor.btnMedicationHistory
import kotlinx.android.synthetic.main.activity_home_hor.btnShowShots
import java.util.*

//Enum con los distintos proveedores de autenticación
enum class ProviderTypeCuidador {
    BASIC, //Email + password
    GOOGLE
}
class HomeActivityCuidador: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_cuidador)
        setup()
    }

    private fun setup(){

        title = getString(R.string.homeTitle)

        btnAddMedicineCuidador.setOnClickListener {
            goToAddMedicine()
        }

        btnShowShotsCuidador.setOnClickListener {
            goToShowSots()
        }

        btnMedicationHistoryCuidador.setOnClickListener {
            goToMedicationHistory()
        }

        //Recuperar extras del intent de SignIn/SignUp Activity
        val bundle = intent.extras
        val email = bundle?.get(getString(R.string.intentEmail))
        val provider = bundle?.get(getString(R.string.provider))

        //Guardar el estado de la app
        val preferences = getSharedPreferences(getString(R.string.preferencesFile), Context.MODE_PRIVATE).edit()
        preferences.putString(getString(R.string.intentEmail), email.toString())
        preferences.putString(getString(R.string.provider), provider.toString())
        preferences.apply()
    }

    private fun goToAddMedicine(){
        val addMedicineIntent = Intent(this, TakePictureActivity::class.java)
        startActivity(addMedicineIntent)
    }

    private fun goToShowSots(){
        val showShotsInIntent = Intent(this, ShowShotsActivity::class.java)
        startActivity(showShotsInIntent)
    }

    private fun goToMedicationHistory(){
        val medicationHistoryIntent = Intent(this, PlanMedicineActivity::class.java)
        startActivity(medicationHistoryIntent)
    }
    //TODO ADRIAN: volver a pantalla principal con las credenciales correctas
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