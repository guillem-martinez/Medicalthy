package com.grupo10.medicalthy

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_show_shots.*
import java.util.concurrent.atomic.AtomicInteger

object RandomUtils {
    private val randomInt = AtomicInteger()

    fun getRandomInt() = randomInt.getAndIncrement() + System.currentTimeMillis().toInt()

    fun dateFormatter(timeInMillis: Long): String =
        android.text.format.DateFormat.format("dd/MM/yyyy hh:mm:ss", timeInMillis).toString()


    fun getMedicineName(cn: String, cback: (String)->Unit){
        FirebaseFirestore.getInstance().collection("medicamentos").document(cn).get().addOnSuccessListener { med ->
            if (med != null) {
                cback(med.get("Nombre").toString())
            } else {
                cback("Error")
            }
        }
    }
}