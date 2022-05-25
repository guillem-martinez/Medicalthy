package com.grupo10.medicalthy

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_plan_medicine.*
import kotlinx.android.synthetic.main.activity_shopping_list.*
import kotlinx.android.synthetic.main.activity_show_shots.*
import java.text.SimpleDateFormat
import java.util.*

class ShoppingList : AppCompatActivity() {
    private var email: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_list)
        addProduct()
        deleteAllProducts()
        val bundle = intent.extras
        email = bundle?.get(getString(R.string.intentEmail)).toString()
        Toast.makeText(this, "email Principio: $email", Toast.LENGTH_LONG).show()

        setup()
    }


    private var i: Int = 0
    private var product: String = ""

    fun showdialog(): String {
        val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Añadir Producto")

        // Set up the input
        val input = EditText(this)
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setHint("Introduzca el producto")
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)


        // Set up the buttons
        builder.setPositiveButton("Añadir", DialogInterface.OnClickListener { dialog, which ->
            // Here you get get input text from the Edittext
            product = input.text.toString()
            if (product.isEmpty()){
                showAlert("El producto debe contener al menos un carácter")
                builder.setView(input)
            }
            else {
                refreshView(product)
            }
        })
        builder.setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
        return product
    }

    private fun addProduct(){
        btnAnadirProducto.setOnClickListener{
            showdialog()
            //refreshView(product)
        }
    }

    private fun makeNewTextView(text: String, tag: String) : TextView {
        val txt_view = TextView(this)
        txt_view.text = text
        txt_view.tag = tag
        txt_view.height = 150
        txt_view.textAlignment = View.TEXT_ALIGNMENT_CENTER
        txt_view.gravity = Gravity.CENTER
        txt_view.textSize = 20F

        i++

        val c: String = if ((i % 2) == 0) "#FFFFFF" else "#E4E3DF"
        txt_view.setBackgroundColor(Color.parseColor(c))


        return txt_view

    }

    fun refreshView(prod: String) {
        Toast.makeText(this, "email es : $email y $prod", Toast.LENGTH_LONG).show()
        linearLayoutList.addView(makeNewTextView(prod, i.toString()))
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(email)
            .collection("ListaCompra").document(prod).set(hashMapOf("nombre" to prod))
    }

    private fun deleteView(){
        linearLayoutList.removeAllViewsInLayout()
    }

    private fun deleteAllProducts(){
        btnEmptyList.setOnClickListener{
            deleteView()

            val usuario = FirebaseFirestore.getInstance().collection("users").document(email)

            usuario.collection("ListaCompra").get().addOnSuccessListener { medicinas->
                var text: String = ""
                var tag: String = ""

                if(medicinas != null){
                    for(medicina in medicinas){


                        usuario.collection("ListaCompra").document(medicina.id).delete()
                    }//usuario.collection("Planes") ... (time_stamp_list)
                    //for(plan in planes)
                }//if(planes != null)
            }//usuario.collection. (planes).addOnSuccessListener


        }
    }

    private fun showAlert(errorMessage : String){
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setTitle("Alerta")
        builder.setMessage(errorMessage)
        builder.setPositiveButton(getString(R.string.acceptMessage), null)
        val dialog : androidx.appcompat.app.AlertDialog = builder.create()
        dialog.show()
    }

    private fun setup(){

        val usuario = FirebaseFirestore.getInstance().collection("users").document(email)

        usuario.collection("ListaCompra").get().addOnSuccessListener { medicinas->
            var text: String = ""
            var tag: String = ""

            if(medicinas != null){
                for(medicina in medicinas){
                    linearLayoutList.addView(makeNewTextView(medicina.id, i.toString()))

                }//usuario.collection("Planes") ... (time_stamp_list)
                //for(plan in planes)
            }//if(planes != null)
        }//usuario.collection. (planes).addOnSuccessListener



    }


}