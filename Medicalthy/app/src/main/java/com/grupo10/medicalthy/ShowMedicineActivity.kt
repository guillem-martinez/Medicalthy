package com.grupo10.medicalthy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.grupo10.medicalthy.RandomUtils.getMedicineDescription
import com.grupo10.medicalthy.RandomUtils.getMedicineName
import kotlinx.android.synthetic.main.activity_show_medicine.*

class ShowMedicineActivity : AppCompatActivity() {

    private var tts = TextToSpeechClass(this)
    private var user: String = ""
    private var cn: String = ""
    private var plan: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_medicine)

        tts.configurationSettings()
        setup()
    }

    private fun setup() {
        title = getString(R.string.app_name)

        val intent_obj: Intent = intent
        cn = intent_obj.getStringExtra("cn").toString()
        user = intent_obj.getStringExtra("user").toString()

        if(cn != null){
            getMedicineName(cn){ name ->
                getMedicineDescription(cn) { desc ->
                    setTextViewInfo(name, desc, "")
                }
            }
        }

        btnSpeak.setOnClickListener {
            tts.speak("Escúchame una cosa viejo demente, o te tomas la pastilla o te vas con San Pedro.")
        }
    }

    private fun setTextViewInfo(name: String, description: String, hours: String){
        lblTitle.text = if(name != "") name else "Nombre no encontrado"
        lblDescription.text = if(description != "") description else "Descripción no encontrada"
        lblShotsList.text = "    * TOMA X [ 0 P ]"
    }

    override fun onDestroy() {
        tts.shutdown()
        super.onDestroy()
    }
}