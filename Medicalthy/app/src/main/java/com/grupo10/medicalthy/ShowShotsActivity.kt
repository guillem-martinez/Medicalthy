package com.grupo10.medicalthy

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_shots)


        val bundle = intent.extras
        email = bundle?.get(getString(R.string.intentEmail)).toString()

        if(email == "" || email == "null")
            email = "email@gmail.com"

        setup()

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

        if(tag != "0,0") {
            txt_view.setOnClickListener {
                val showMedicineIntent = Intent(this, ShowMedicineActivity::class.java)
                showMedicineIntent.putExtra("cn_plan", txt_view.getTag().toString())
                showMedicineIntent.putExtra("user", email)
                startActivity(showMedicineIntent)
            }
        }
        return txt_view
    }


    private fun setPlanButtons(email: String){
        val map_list_view = emptyMap<String, LinearLayout>().toMutableMap()

        val usuario = FirebaseFirestore.getInstance().collection("users").document(email)

        usuario.collection("Planes").get().addOnSuccessListener { planes->
            var text: String = ""
            var tag: String = ""

            if(planes != null){
                for(plan in planes){
                    usuario.collection("Planes").document(plan.id).collection("Tomas").get().addOnSuccessListener { time_stamp_list ->
                        for(time_stamp in time_stamp_list){
                            Log.d("times_stamp.id --> ", time_stamp.id.toString())
                            val hour: String = SimpleDateFormat("HH:mm").format(Date(time_stamp.id.toLong()))
                            getMedicineName(plan.get("CN").toString()) { name ->
                                val cn = plan.get("CN").toString()
                                vertical_layout.addView(
                                    makeNewTextView(
                                        "$name -> $hour",
                                        cn + "," + plan.id
                                    )
                                )
                            }//getMedicineName() ->
                        }//for(time_stam in time_stamp_list)
                    }//usuario.collection("Planes") ... (time_stamp_list)
                }//for(plan in planes)
            }//if(planes != null)
        }//usuario.collection. (planes).addOnSuccessListener
    }//setPlanButtons
}