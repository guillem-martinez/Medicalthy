package com.grupo10.medicalthy

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.*

class TextToSpeechClass(context: Context) {

    private lateinit var tts: TextToSpeech
    private val activityContext = context
    private val utteranceId = RandomUtils.getRandomInt().toString() //Id para identificar la petición

    fun getUtteranceId(): String { return utteranceId }

    //Configura el idioma del Text Speech y la velocidad a la se leerá el texto
    fun configurationSettings() {
        tts = TextToSpeech(activityContext, TextToSpeech.OnInitListener {
            if(it == TextToSpeech.SUCCESS) {

                val locale = Locale("es", "ES")
                tts.language = locale
                tts.setSpeechRate(1.0f)
                //tts.defaultEngine
                //tts.defaultVoice
            }
        })
    }

    //Función que lee el mensaje que se le pasa por parámetro
    fun speak(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
    }

    fun shutdown() {
        tts.stop()
        tts.shutdown()
    }
}