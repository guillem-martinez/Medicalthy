package com.grupo10.medicalthy


import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.core.view.isEmpty
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_show_shots.vertical_layout

class ShowHistoryActivity : AppCompatActivity() {
    var x : Int = 0
    var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_history)


        val bundle = intent.extras
        email = bundle?.get("email").toString()

        setup()

        setHistoryButtons(email)
    }


    private fun setup() {
        title = getString(R.string.app_name)
    }

    private fun setHistoryButtons(email: String){
        val usuario = FirebaseFirestore.getInstance().collection("users").document(email)

        usuario.collection("Planes").get().addOnSuccessListener { planes->
            var text: String = ""
            var tag: String = ""

            if(planes != null){
                for(plan in planes){
                    usuario.collection("Planes").document(plan.id).collection("Historial").get().addOnSuccessListener { history_element_list ->
                        for(history_element in history_element_list) {
                            vertical_layout.addView(
                                makeNewTextView(
                                    history_element.id + " -> " + history_element["tomado"].toString(),
                                    "null"
                                )
                            )
                        }//for(history_element in history_element_list)
                    }//usuario.collection("Planes") ... (time_stamp_list)
                }//for(plan in planes)
            }//if(planes != null)
        }//usuario.collection. (planes).addOnSuccessListener
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

        if(tag != "0,0") {


        }
        return txt_view
    }
}