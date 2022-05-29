package com.grupo10.medicalthy

import android.Manifest
import android.net.Uri
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.android.synthetic.main.activity_plan_medicine.*
import kotlinx.android.synthetic.main.activity_start.*
import com.grupo10.medicalthy.RandomUtils.getMedicineName
import com.grupo10.medicalthy.RandomUtils.getNCFromString
import kotlinx.android.synthetic.main.activity_take_pic.*


class TakePictureActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_pic)

        button.isEnabled = false
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 111)
        } else {
            button.isEnabled = true
        }
        button.setOnClickListener {
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 101)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 101 && data != null){
            val uri = applicationContext.drawableToUri(R.drawable.prueba2)
            imageView.setImageURI(null)
            imageView.setImageURI(uri)
            //val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri)
            //val image = InputImage.fromBitmap(bitmap, 0)
            imageView.setImageBitmap(data.extras?.get("data") as? Bitmap)
            val bitmap = data.extras?.get("data") as Bitmap
            val image = InputImage.fromBitmap(bitmap, 0)
            var nc = "";
            val result = recognizer.process(image)
            result.addOnSuccessListener { recognitions ->
                val text = recognitions.text
                nc = getNCFromString(text)
                //Toast.makeText(this, nc, Toast.LENGTH_SHORT).show()


                val view = StringBuilder()
                if (nc == "ERROR") {
                    view.append("ERROR, no se ha leido bien la imagen ")
                    textView4.text = view.toString()
                } else {
                    Toast.makeText(this, nc, Toast.LENGTH_SHORT).show()
                    view.append("Código encontrado: ").append(nc)
                    textView4.text = view.toString()
                    var nombre_bd = ""
                    getMedicineName(nc){ name ->
                        nombre_bd = name
                    }
                    if (nombre_bd != "Error") {
                        goToAddMedicine(nc)
                    } else {
                        goToAddMedicine("")
                    }
                }
            }
            result.addOnFailureListener {
                val view = StringBuilder()
                view.append("Ha ocurrido un error")
                textView4.text = view.toString()
            }



        }
    }

    fun Context.drawableToUri(drawable: Int):Uri{
        return Uri.parse("android.resource://$packageName/$drawable")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 111) {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                button.isEnabled = true
            }
        }
    }

    private fun goToAddMedicine(nc : String){

        val bundle = intent.extras
        val email = bundle?.get(getString(R.string.intentEmail))

        val addMedicineIntent = Intent(this, PlanMedicineActivity::class.java).apply {
            putExtra("nc",nc)
            putExtra(getString(R.string.intentEmail)   , email.toString())

            //Lo pasa el home, identifica si debe volver a HomeActivity o bien a HomeActivityCuidador
            val bundle = intent.extras
            putExtra("home", bundle?.get("home").toString())
        }

        startActivity(addMedicineIntent)
    }
}