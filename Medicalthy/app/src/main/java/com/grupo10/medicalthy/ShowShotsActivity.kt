package com.grupo10.medicalthy

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import com.google.firebase.firestore.FirebaseFirestore
import com.grupo10.medicalthy.RandomUtils.getMedicineName
import kotlinx.android.synthetic.main.activity_show_shots.*
import java.text.SimpleDateFormat
import java.util.*

class ShowShotsActivity : AppCompatActivity() {
    var x : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_shots)

        setup()

        var email = "usuario1@gmail.com"

        setPlanButtons(email)
    }

    private fun setup() {
        title = getString(R.string.app_name)
    }

    private fun makeNewTextView(text: String, tag: String) : TextView{
        var txt_view = TextView(this)
        txt_view.text = text
        txt_view.tag = tag
        txt_view.height = 100
        txt_view.textAlignment= View.TEXT_ALIGNMENT_CENTER
        txt_view.gravity = Gravity.CENTER

        x++

        val c : String = if((x % 2) == 0) "#FF7C70" else "#FF9E8F"
        txt_view.setBackgroundColor(Color.parseColor(c))

        txt_view.setOnClickListener{
            txt_view.text = txt_view.getTag().toString()
        }
        return txt_view
    }


    private fun setPlanButtons(email: String){
        val map_list_view = emptyMap<String, LinearLayout>().toMutableMap()

        val usuario = FirebaseFirestore.getInstance().collection("users").document(email)

        usuario.collection("Planes").get().addOnSuccessListener { planes->
            var text: String = ""
            var tag: String = ""

            for(plan in planes){ //Por cada plan en la colección de planes
                usuario.collection("Planes").document(plan.id).collection("Tomas").document("Horas").get().addOnSuccessListener { horas ->
                    for(hora in horas.data!!.values){
                        val str_seconds = hora.toString().substring(18, 28)
                        val hour : String = SimpleDateFormat("HH:mm").format(Date(str_seconds.toLong() * 1000))

                        getMedicineName(plan.get("CN").toString()){ name ->
                            vertical_layout.addView(makeNewTextView("$name -> $hour", plan.get("CN").toString()))
                        }
                    }
                }
            }
            if(planes.isEmpty){
                vertical_layout.addView(makeNewTextView("No se ha encontrado ningún plan en tu usuario", "0"))
            }
        }
    }
}