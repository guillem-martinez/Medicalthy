package com.grupo10.medicalthy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_show_medicine.*

class ShowMedicineActivity : AppCompatActivity() {

    private var tts = TextToSpeechClass(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_medicine)

        tts.configurationSettings()
        setup()
    }

    private fun setup() {
        title = getString(R.string.app_name)

        btnSpeak.setOnClickListener {
            tts.speak("Escúchame una cosa viejo demente, o te tomas la pastilla o te vas con San Pedro.")
        }

    }


    override fun onDestroy() {
        tts.shutdown()
        super.onDestroy()
    }
}