package com.grupo10.medicalthy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

//Enum con los distintos proveedores de autenticación
enum class ProviderType {
    BASIC //Email + password
    //Google
}
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setup()
    }

    private fun setup(){

        title = getString(R.string.homeTitle)
    }
}