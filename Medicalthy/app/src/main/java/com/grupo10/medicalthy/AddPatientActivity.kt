package com.grupo10.medicalthy

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_patient.*


class AddPatientActivity : AppCompatActivity() {
    var x: Int = 0
    var email = "usuario1@gmail.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_patient)

        setPatientButtons(email)

        setup()
    }

    private fun setup() {
        title = getString(R.string.app_name)

    }

    private fun setPatientButtons(email: String){
        val usuario = FirebaseFirestore.getInstance().collection("users").document(email)
        usuario.collection("cuida_a").get().addOnSuccessListener { patients->
            for(patient in patients){
                Log.d("Patient-email -> ", patient.id.toString())
                FirebaseFirestore.getInstance().collection("users").document(patient.id).get().addOnSuccessListener {
                    Log.d("Pantent --> ", it.id)
                    vertical_layout.addView(makeNewTextView(it["Surnames"].toString() + ", "+ it["Name"].toString(), patient.id))
                }
            }
        }
    }

    private fun makeNewTextView(text: String, tag: String) : TextView {
        var txt_view = TextView(this)
        txt_view.text = text
        txt_view.tag = tag
        txt_view.height = 100
        txt_view.textAlignment= View.TEXT_ALIGNMENT_CENTER
        txt_view.gravity = Gravity.CENTER

        x++

        val c : String = if((x % 2) == 0) "#FF7C70" else "#FF9E8F"
        txt_view.setBackgroundColor(Color.parseColor(c))

        if(tag != "0") {
            txt_view.setOnClickListener {
                val homeActivityCuidadorIntent = Intent(this, HomeActivityCuidador::class.java)
                homeActivityCuidadorIntent.putExtra("email", txt_view.getTag().toString())
                startActivity(homeActivityCuidadorIntent)
            }
        }
        return txt_view
    }

}