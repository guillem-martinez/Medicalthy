package com.grupo10.medicalthy

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.grupo10.medicalthy.RandomUtils.getMedicineDescription
import com.grupo10.medicalthy.RandomUtils.getMedicineName
import com.grupo10.medicalthy.RandomUtils.getNPills
import kotlinx.android.synthetic.main.activity_show_medicine.*
import java.util.*
import kotlin.reflect.typeOf

class ShowMedicineActivity : AppCompatActivity() {

    private var tts = TextToSpeechClass(this)
    private var email: String = ""
    private var cn: String = ""
    private var plan: String = ""
    private var n_pills: Int = 0 //TODO:Cargar el número de pastillas de la base de datos para ese plan
    private val PILLS_THRESHOLD: Int = 10 //Por debajo de 10 pastillas salta el aviso de fin de existencias
    lateinit var selectedImage: ImageView
    lateinit var storage : FirebaseStorage




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_medicine)

        selectedImage = findViewById(R.id.imageView2)
        storage = Firebase.storage

        tts.configurationSettings()
        setup()
    }

    private fun setup() {
        title = getString(R.string.app_name)

        val intent_obj: Intent = intent
        val cn_plan = intent_obj.getStringExtra("cn_plan").toString()

        cn = cn_plan.split(",")[0].toString()
        plan = cn_plan.split(",")[1].toString()
        email = intent_obj.getStringExtra("email").toString()

        if(cn != null){
            getMedicineName(cn){ name ->
                getMedicineDescription(cn) { desc ->
                    setTextViewInfo(name, desc, "")
                    updateImage()
                }
            }
        }

        btnSpeak.setOnClickListener {
            getMedicineName(cn) { name ->
                getMedicineDescription(cn) { desc ->
                    tts.speak("$name + un comprimido")
                }
            }
        }

        btnOk.setOnClickListener{
            btnOkPressed()
            enOfStock()
        } //TODO: n_pills - 1 y actualizar BD
        btnNoOk.setOnClickListener{ btnNoOkPressed() }
    }

    private fun btnOkPressed(){
        var result = FirebaseFirestore.getInstance()
            .collection("users")
            .document(email)
            .collection("Planes")
            .document(plan)
            .collection("Historial")
            .document(Date().toString()).set(mapOf("tomado" to true))
    }

    private fun btnNoOkPressed(){
        var result = FirebaseFirestore.getInstance()
            .collection("users")
            .document(email)
            .collection("Planes")
            .document(plan)
            .collection("Historial")
            .document(Date().toString()).set(mapOf("tomado" to false))
    }

    private fun setTextViewInfo(name: String, description: String, hours: String){
        lblTitle.text = if(name != "") name else "Nombre no encontrado"
        lblDescription.text = if(description != "") description else "Descripción no encontrada"
        lblShotsList.text = "    * TOMA X [ 0 P ]✅ ❌"
    }

    private fun enOfStock() {
        getNPills(plan, email) { pills ->
            if(pills < PILLS_THRESHOLD) {
                //Si salta aviso de fin de existencias mostrar mensaje de si quiere añadirlo a la lista de la compra
                //si pulsa si se añade el producto a la lista de la compra, recoger nombre medicamento
                //si pulsa no no se hace nada
                android.app.AlertDialog.Builder(this).apply {
                    setTitle("Fin existencias")
                    setMessage("Te quedaste sin pastis mi bro")

                    setPositiveButton(getString(R.string.yesMessage)) { _, _ ->
                        getMedicineName(cn){ name ->
                            ShoppingList().refreshView(name)
                        }
                    }

                    setNegativeButton(getString(R.string.noMessage)){_, _ ->
                    }

                    setCancelable(true)
                }.create().show()
            }
        }
    }

    private fun updateImage(){
        var result = FirebaseFirestore.getInstance()
            .collection("users")
            .document(email)
            .collection("Planes")
            .document(plan).get().addOnSuccessListener { med ->
                if (med.get("url") != null){

                    val url = med.get("url")
                    val storageRef = storage.reference

                    var islandRef = storageRef.child(url as String)

                    val ONE_MEGABYTE: Long = 1024 * 1024

                    islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {
                        var bmp = BitmapFactory.decodeByteArray(it, 0, it.size)

                        selectedImage.setImageBitmap(bmp)


                        // Data for "images/island.jpg" is returned, use this as needed
                    }.addOnFailureListener {
                        // Handle any errors
                    }



                    //selectedImage.setImageBitmap(imageBitmap)


                }

            }

    }

    override fun onDestroy() {
        tts.shutdown()
        super.onDestroy()
    }
}