package com.grupo10.medicalthy

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_show_shots.*
import java.util.concurrent.atomic.AtomicInteger

object RandomUtils {
    private val randomInt = AtomicInteger()

    fun getRandomInt() = randomInt.getAndIncrement() + System.currentTimeMillis().toInt()

    fun dateFormatter(timeInMillis: Long): String =
        android.text.format.DateFormat.format("dd/MM/yyyy HH:mm:ss", timeInMillis).toString()

    fun hourFormatter(timeInMillis: Long): String =
        android.text.format.DateFormat.format("HH:mm", timeInMillis).toString()


    fun getMedicineName(cn: String, cback: (String)->Unit){
        FirebaseFirestore.getInstance().collection("medicamentos").document(cn).get().addOnSuccessListener { med ->
            if (med != null) {
                cback(med.get("Nombre").toString())
            } else {
                cback("Error")
            }
        }
    }

    fun getMedicineDescription(cn: String, cback: (String)->Unit){
        FirebaseFirestore.getInstance().collection("medicamentos").document(cn).get().addOnSuccessListener { med ->
            if (med != null) {
                cback(med.get("Descripcion").toString())
            } else {
                cback("Error")
            }
        }
    }

    fun getNCFromString(text: String): String {
        val text_parts = text.split("\n")
        for (i in text_parts) {
            val prueba = i.filter { it.isDigit() }
            if (prueba.length == 7) {
                val nc = StringBuilder()
                nc.append(prueba[0]).append(prueba[1]).append(prueba[2]).append(prueba[3]).append(prueba[4])
                    .append(prueba[5]).append('.').append(prueba[6])
                return nc.toString()
            }
            if (prueba.length == 6) {
                return prueba
            }
        }
        return "ERROR"
    }
}