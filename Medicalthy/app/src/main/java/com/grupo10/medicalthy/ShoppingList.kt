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
import kotlinx.android.synthetic.main.activity_plan_medicine.*
import kotlinx.android.synthetic.main.activity_shopping_list.*

class ShoppingList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_list)
        addProduct()
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
            refreshView(product)
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

    private fun refreshView(prod: String) {
        linearLayoutList.addView(makeNewTextView(prod, i.toString()))
    }


}