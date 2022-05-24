package com.grupo10.medicalthy

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
//import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home_hor.*
import java.util.*

//Enum con los distintos proveedores de autenticación
enum class ProviderType {
    BASIC, //Email + password
    GOOGLE
}
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_hor)
        setup()
    }

    private fun setup(){

        title = getString(R.string.homeTitle)

        //Recuperar extras del intent de SignIn/SignUp Activity
        val bundle = intent.extras
        val email = bundle?.get(getString(R.string.intentEmail))
        val provider = bundle?.get(getString(R.string.provider))

        btnAddMedicine.setOnClickListener {
            goToAddMedicine(email.toString())
        }

        btnShowShots.setOnClickListener {
            goToShowSots(email.toString())
        }

        btnPharmacy.setOnClickListener {
            goToPharmacyMap()
        }

        btnMedicationHistory.setOnClickListener {
            goToMedicationHistory()
        }
        btnShoppingCart.setOnClickListener {
            goToShoppingList()
        }



        //Guardar el estado de la app
        val preferences = getSharedPreferences(getString(R.string.preferencesFile), Context.MODE_PRIVATE).edit()
        preferences.putString(getString(R.string.intentEmail), email.toString())
        preferences.putString(getString(R.string.provider), provider.toString())
        preferences.apply()
    }

    private fun goToAddMedicine(email : String){

        val addMedicineIntent = Intent(this, TakePictureActivity::class.java).apply{
            putExtra(getString(R.string.intentEmail), email)
        }

        startActivity(addMedicineIntent)
    }

    private fun goToShowSots(email : String){
        val showShotsInIntent = Intent(this, ShowShotsActivity::class.java).apply{
            putExtra(getString(R.string.intentEmail), email)
        }
        startActivity(showShotsInIntent)
    }

    private fun goToPharmacyMap(){
        val pharmacyMapIntent = Intent(this, PharmacyMapActivity::class.java)
        startActivity(pharmacyMapIntent)
    }

    private fun goToMedicationHistory(){
        val medicationHistoryIntent = Intent(this, ShowHistoryActivity::class.java)
        startActivity(medicationHistoryIntent)
    }

    private fun goToShoppingList(){
        val shoppingListIntent = Intent(this, ShoppingList::class.java)
        startActivity(shoppingListIntent)
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