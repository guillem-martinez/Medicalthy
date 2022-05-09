package com.grupo10.medicalthy

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_add_medicine.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.util.*


class AddMedicineActivity : AppCompatActivity() {

    lateinit var selectedImage: ImageView
    lateinit var cameraBtn: Button
    lateinit var currentPhotoPath: String
    lateinit var storage : FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_medicine)

        storage = Firebase.storage

        setup()
    }

    private fun setup() {
        title = getString(R.string.app_name)

        selectedImage = findViewById(R.id.displayImageView)
        cameraBtn = findViewById(R.id.cameraBtn)


        cameraBtn.setOnClickListener(View.OnClickListener { view ->

            //.makeText(this, "Notificación corta", Toast.LENGTH_SHORT).show()
            askCameraPermissions()


        })




    }

    private fun askCameraPermissions() {
        if((ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) &&
            (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE) , 101)


        }else {

            Toast.makeText(this, "Notificación askCameraPermissions", Toast.LENGTH_SHORT).show()

            openCamera()
            //dispatchTakePictureIntent()
        }

    }


    private fun openCamera() {
        var camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(camera, 102)

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 102 ){

            Toast.makeText(this, "Notificación onActivtyResult", Toast.LENGTH_SHORT).show()


            val imageBitmap = data?.extras?.get("data") as Bitmap?
            selectedImage.setImageBitmap(imageBitmap)






            if (imageBitmap != null) {
                uploadFile(imageBitmap)
            }

            /*
            val f = File(currentPhotoPath)
            selectedImage.setImageURI(Uri.fromFile(f))
            */

        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 101){
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                Toast.makeText(this, "Notificación onRequestPermissionsResult", Toast.LENGTH_SHORT).show()
                Log.d("tag", "hola")


                openCamera()
                //dispatchTakePictureIntent()


            }else{

                showAlert(getString(R.string.takePicPermission))

            }
        }

    }


    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    val REQUEST_TAKE_PHOTO = 1

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    Log.d("tag","photoFilehere")
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.grupo10.android.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }




    private fun showAlert(errorMessage : String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.simpleErrorMessage))
        builder.setMessage(errorMessage)
        builder.setPositiveButton(getString(R.string.acceptMessage), null)
        val dialog : AlertDialog = builder.create()
        dialog.show()
    }

    internal inner class MyFailureListener : OnFailureListener{
        override fun onFailure(exception: Exception) {
            val errorCode = (exception as StorageException).errorCode
            val errorMessage = exception.message




        }


    }


    fun uploadFile(imageBitmap : Bitmap){


        val storageRef = storage.reference





        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageRef = storageRef.child(timeStamp+".jpg")

        """var imageRef = storageRef.child("images")
        val filename = "$timeStamp.jpg"

        var spaceRef = imageRef.child(filename)

        val path = spaceRef.path

        val name = spaceRef.name

        imageRef = spaceRef.parent!!"""





        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG,100, baos)
        val data = baos.toByteArray()

        var uploadTask = imageRef.putBytes(data)




        uploadTask.addOnFailureListener{


            // Handle unsuccessful uploads
            MyFailureListener()

        }.addOnSuccessListener { taskSnapshot ->

            Toast.makeText(this, "HOLAAAAAAAAAAA onActivtyResult", Toast.LENGTH_SHORT).show()

        }

    }

}