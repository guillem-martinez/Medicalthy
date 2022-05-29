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
import com.grupo10.medicalthy.RandomUtils.deleteCuidaA
import com.grupo10.medicalthy.RandomUtils.deleteResponsable
import kotlinx.android.synthetic.main.activity_add_patient.*


class AddPatientActivity : AppCompatActivity() {
    var x: Int = 0
    var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_patient)

        setup()
    }

    private fun setup() {
        title = getString(R.string.app_name)


        val bundle = intent.extras
        email = bundle?.get(getString(R.string.intentEmail)).toString()
        val provider = bundle?.get(getString(R.string.provider))

        if(email == null || email == "" ||email == "null"){
            email = "usuario1@gmail.com"
        }

        setPatientButtons(email)

        anadirPaciente.setOnClickListener {
            var signInPatientIntent = Intent(this, SignInPatientActivity::class.java)
            signInPatientIntent.putExtra("email", email)
            startActivity(signInPatientIntent)
        }
    }

    private fun setPatientButtons(email: String){
        val usuario = FirebaseFirestore.getInstance().collection("users").document(email)
        usuario.collection("cuida_a").get().addOnSuccessListener { patients->
            for(patient in patients){
                FirebaseFirestore.getInstance().collection("users").document(patient.id).get().addOnSuccessListener {
                    vertical_layout.addView(makeNewTextView(it["Surnames"].toString() + ", "+ it["Name"].toString(), patient.id))
                }
            }
        }
    }

    private fun makeNewTextView(text: String, tag: String) : TextView {
        var txt_view = TextView(this)
        txt_view.text = text
        txt_view.tag = tag
        txt_view.height = 150
        txt_view.textSize = 20F
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

            txt_view.setOnLongClickListener {
                deleteResponsable(email, txt_view.getTag().toString()) {
                    if(it){
                        deleteCuidaA(email, txt_view.getTag().toString()) { it2 ->
                            if (it2) {
                                x = 0   //Reinicia el contador para que empiece con el color 0
                                vertical_layout.removeAllViews()
                                setPatientButtons(email)
                            }
                        }//deleteCuidaA(...)
                    }//if(it)
                }//deleteResponsable(...)

                //TODO: Al hacer todo esto, debería mostrar un mensaje "Se ha eliminado conexión con paciente" o similar
                return@setOnLongClickListener true
            }//txt_view.setOnLongClickListener
        }
        return txt_view
    }

}