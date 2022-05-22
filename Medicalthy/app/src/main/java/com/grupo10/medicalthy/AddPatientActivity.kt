package com.grupo10.medicalthy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class AddPatientActivity : AppCompatActivity() {
    var x: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_patient)



        setup()
    }

    private fun setup() {
        title = getString(R.string.app_name)

    }

}