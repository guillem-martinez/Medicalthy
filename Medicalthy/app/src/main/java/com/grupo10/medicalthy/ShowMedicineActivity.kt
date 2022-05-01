package com.grupo10.medicalthy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ShowMedicineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_medicine)

        setup()
    }

    private fun setup() {
        title = getString(R.string.app_name)
    }
}