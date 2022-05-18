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
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.android.synthetic.main.activity_take_pic.*


class TakePictureActivity : AppCompatActivity() {

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
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri)
            val image = InputImage.fromBitmap(bitmap, 0)
            // imageView.setImageBitmap(data.extras?.get("data") as Bitmap)
            // val image = InputImage.fromBitmap(data.extras?.get("data") as Bitmap, 0)
            val result = recognizer.process(image)
            result.addOnSuccessListener { recognitions ->
                println("\n\nSuccess\n\n")
                val text = recognitions.text
                val nc = getNC(text)
                //Toast.makeText(this, nc, Toast.LENGTH_SHORT).show()
                val view = StringBuilder()
                view.append("Còdigo encontrado: ").append(nc)
                textView4.text = view.toString()
                goToAddMedicine(nc)
            }
            result.addOnFailureListener { println("\n\nFailure\n\n") }
            print(result)
        }
    }

    fun Context.drawableToUri(drawable: Int):Uri{
        return Uri.parse("android.resource://$packageName/$drawable")
    }

    fun getNC(text: String): String {
        val text_parts = text.split("\n")
        for (i in text_parts) {
            val prueba = i.filter { it.isDigit() }
            if (prueba.length == 7) {
                val nc = StringBuilder()
                nc.append(prueba[0]).append(prueba[1]).append(prueba[2]).append(prueba[3]).append(prueba[4])
                    .append(prueba[5]).append('.').append(prueba[6])
                return nc.toString()
            }
            if (prueba.length == 6) {
                return prueba
            }
        }
        return "ERROR"
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
        val addMedicineIntent = Intent(this, PlanMedicineActivity::class.java).also {
            it.putExtra("nc",nc)
            startActivity(it)
        }
    }



}