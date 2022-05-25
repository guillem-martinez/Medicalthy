package com.grupo10.medicalthy

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_plan_medicine.*
import java.util.*
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.provider.MediaStore
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import com.google.firebase.Timestamp
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.grupo10.medicalthy.RandomUtils.getMedicineName
import kotlinx.android.synthetic.main.activity_shopping_list.*
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit
import kotlin.math.abs


class PlanMedicineActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    lateinit var notifications: Notifications
    var calendar: Calendar = Calendar.getInstance()
    private var timeMillis: Long = 0
    private var initialDate: Long = 0
    private var timeInMillisList: MutableList<Long> = arrayListOf()
    private var nc : String = ""
    private var i : Int = 0
    //Variables para añadir imagen:
    lateinit var selectedImage: ImageView
    lateinit var cameraBtn: Button
    lateinit var currentPhotoPath: String
    lateinit var storage : FirebaseStorage
    private var imageBitmap : Bitmap? = null
    private var planToken : String = ""
    lateinit var email : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan_medicine)

        val bundle = intent.extras

        email = bundle?.get(getString(R.string.intentEmail)).toString()
        nc = bundle?.get("nc").toString()
        //nc = intent.getStringExtra("nc")
        storage = Firebase.storage

        //Esto es para test, si uno de los dos elementos no viene seteado (inicias PlanMedicine directamente)
        //se setea a un valor por defecto. De esta forma, no hay que ir cambiando directamente en el código
        if(nc == "" || nc == "null")
            nc = "658257.2"
        if(email == "" || email == "null")
            email = "email@gmail.com"

        setup()
    }

    private fun setMillis(timeInMillis : Long){
        this.timeMillis = timeInMillis
        timeInMillisList.add(timeInMillis)
    }

    private fun makeNewTextView(text: String, tag: String) : TextView {
        val txt_view = TextView(this)
        txt_view.text = text
        txt_view.tag = tag
        txt_view.height = 100
        txt_view.textAlignment = View.TEXT_ALIGNMENT_CENTER
        txt_view.gravity = Gravity.CENTER

        i++

        val c: String = if ((i % 2) == 0) "#FFFFFF" else "#E4E3DF"
        txt_view.setBackgroundColor(Color.parseColor(c))

        return txt_view

    }

    private fun setup() {
        title = getString(R.string.planMedicineTitle)

        var nombreFinal = ""
        nc?.let { getMedicineName(it) { name ->
            MedicineName.setText(name)
            nombreFinal = name
        } }
        val codigoN = "658257.2"
        /*
        getMedicineName(codigoN) { name ->
            nombreFinal = name
        }

         */
        CodigoNacional.setText("El codigo nacional es: $nc")


        selectedImage = findViewById(R.id.imageView4)
        cameraBtn = findViewById(R.id.button2)

        //Botón para tomar la foto, donde se le piden los permisos al usuario.
        cameraBtn.setOnClickListener(View.OnClickListener { view ->

            askCameraPermissions()


        })

        addDate.setOnClickListener {
            chooseInitialDate()
            Toast.makeText(this, RandomUtils.dateFormatter(initialDate), Toast.LENGTH_LONG).show()
        }

        addHour.setOnClickListener {
            chooseInitialHour { timeInMillis -> timeInMillisList.add(timeInMillis) }

        }

        btnCleanHours.setOnClickListener {
            deleteView()
        }

        saveButton.setOnClickListener {



            //Pasamos los numeros de dias a Milisegundos y lo sumamos con la fecha de inicio:


            if(numDays.text.isNotEmpty() && nComprimidos.text.isNotEmpty()){
                val nPastillas = nComprimidos.text.toString()
                val daysInMilis = TimeUnit.DAYS.toMillis(numDays.text.toString().toLong())
                val finalDate = daysInMilis +  initialDate

                val startDate = Timestamp(Date(initialDate))
                val finishDate = Timestamp(Date(finalDate))

                var data = hashMapOf(
                    "CN" to codigoN,
                    "Finish" to finishDate,
                    "Start" to startDate,
                    "n_pastillas" to nPastillas,
                )

                if(imageBitmap != null){
                    val urlImage = uploadFile(imageBitmap!!)
                    data = hashMapOf(
                        "CN" to codigoN,
                        "Finish" to finishDate,
                        "Start" to startDate,
                        "n_pastillas" to nPastillas,
                        "nombre" to nombreFinal,
                        "codigo" to codigoN,
                        "url" to urlImage
                    )
                }

                //TODO: Falta crear la colección de horas (ver usuario1@gmail.com -> Planes -> Plan0 -> Tomas)
                //                                                                             ^ A partir de ahora es un token
                db.collection("users").document(email).collection("Planes").add(data)
                    .addOnSuccessListener { documentReference ->

                        timeInMillisList.forEach{
                            documentReference.collection("Tomas").document(it.toString()).set(mapOf("n_pastillas" to 1))
                            //n_pastillas = 1 por defecto
                        }
                        planToken = documentReference.id
                        val cnPlan = ("$nc,$planToken")
                        notifications = Notifications(this, Constants.ActivityRef.ShowMedicineActivity.ordinal, cnPlan, email)
                        notifications.createNotificationChannel() //Canal de notificaciones creado
                        sendAlarms()

                        Log.d(TAG, "Document written")
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "error adding document", e)
                    }
            }else{
                checkFieldsAreFilled()
            }

        }
    }

    //Funcion que muestra el calendario para elegir el dia
    private fun chooseInitialDate(){
        calendar.apply {
            this.set(Calendar.SECOND, 0)
            this.set(Calendar.MILLISECOND, 0)
            DatePickerDialog(
                this@PlanMedicineActivity,
                0,//DialogFragment.STYLE_NO_INPUT, // Otra opción para elegir es 0.
                //Valores del año,mes y dia que eligirá el usuario
                { _, year, month, day ->
                    this.set(Calendar.YEAR, year)
                    this.set(Calendar.MONTH, month)
                    this.set(Calendar.DAY_OF_MONTH, day)
                    initialDate = this.timeInMillis
                    refreshDate()
                },
                //Valores del año,mes y dia actual que tomará por defecto el date picker
                this.get(Calendar.YEAR),
                this.get(Calendar.MONTH),
                this.get(Calendar.DAY_OF_MONTH),
            ).apply {
                //La fecha minima para elegir es la del dia actual
                datePicker.minDate = (System.currentTimeMillis() - 1000)
            }.show()
        }
    }

    //Selección de hora y devuelve por lambda el tiempo en milisegundos
    //Tiempo en milisegundos (Dia + hora)
    private fun chooseInitialHour(timeInMillis: (Long) -> Unit) {
        calendar.apply {
            this.set(Calendar.SECOND, 0)
            this.set(Calendar.MILLISECOND, 0)
            TimePickerDialog(
                this@PlanMedicineActivity,
                DialogFragment.STYLE_NO_INPUT, // Otra opción 0
                { _, hour, min ->
                    this.set(Calendar.HOUR_OF_DAY, hour)
                    this.set(Calendar.MINUTE, min)
                    timeInMillis(this.timeInMillis)
                    refreshList()
                },
                this.get(Calendar.HOUR_OF_DAY),
                this.get(Calendar.MINUTE),
                true
            ).show()
        }
    }

    private fun resetCalendar() {
        calendar = Calendar.getInstance()
    }

    private fun goHome(){
        //Como se puede llamar desde dos HomeActivity distintos, es necesario comprobar primero desde cual ha sido llamado
        //Para evitar inconsistencias, el caso por defecto es siempre HomeActivity, pero HomeActivityCuidador
        //siempre enviará su nombre
        var home: String = intent.getStringExtra("home").toString()

        Log.d("TEST====================", home);

        if(home == "HomeActivityCuidador"){


            val homeCuidadorIntent = Intent(this, HomeActivityCuidador::class.java).apply {
                putExtra(getString(R.string.intentEmail)   , email.toString())
            }
            startActivity(homeCuidadorIntent)
        }
        else{
            val homeIntent = Intent(this, HomeActivity::class.java).apply{
                putExtra(getString(R.string.intentEmail)   , email.toString())
            }
            startActivity(homeIntent)
        }


    }

    private fun setAlarms() {
            val nDays = numDays.text.toString().toInt()
            timeInMillisList.forEach {
                Log.d("Timer", "La lista de timeInMillis es: $it")

                if(nDays > 1) {
                    notifications.setRepetitiveAlarm(it, nDays)
                    Toast.makeText(this, RandomUtils.dateFormatter(it), Toast.LENGTH_LONG).show()
                }else {
                    notifications.setExactAlarm(it)
                    Toast.makeText(this, RandomUtils.dateFormatter(it), Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun responsibleConsumption(): Boolean {
        val numList = timeInMillisList.size
        timeInMillisList.sort()

            for (index in 0 until numList - 1) {
                val hour =
                    android.text.format.DateFormat.format("HH", timeInMillisList[index]).toString()
                        .toInt()
                val nextHour =
                    android.text.format.DateFormat.format("HH", timeInMillisList[index + 1])
                        .toString().toInt()

                val result = (hour - nextHour)
                if (abs(result) < 5) {
                    return false
                }
            }
        return true
    }

    private fun showAlert(errorMessage : String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.responsibleConsumption))
        builder.setMessage(errorMessage)
        builder.setPositiveButton(getString(R.string.acceptMessage), null)
        val dialog : AlertDialog = builder.create()
        dialog.show()
    }

    private fun onSavePressed() {
        android.app.AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.responsibleConsumption))
            setMessage(getString(R.string.consumoResponsable))

            setPositiveButton(getString(R.string.yesMessage)) { _, _ ->
                setAlarms()
                resetCalendar()
                goHome()
            }

            setNegativeButton(getString(R.string.noMessage)){_, _ ->
            }

            setCancelable(true)
        }.create().show()
    }

    private fun checkFieldsAreFilled() {
        //TODO: Comprobar que la lista de horas y el inicio del plan de toma no estén vacíos
        if(numDays.text.isEmpty()) {
            showAlert(getString(R.string.NotificationNumDays))
        }
    }


    private fun askCameraPermissions() {
        if((ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) &&
            (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE) , 101)


        }else {

            //Toast.makeText(this, "Notificación askCameraPermissions", Toast.LENGTH_SHORT).show()

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
            //Toast.makeText(this, "Notificación onActivtyResult", Toast.LENGTH_SHORT).show()
            imageBitmap = (data?.extras?.get("data") as Bitmap?)!!
            selectedImage.setImageBitmap(imageBitmap)



            //Comentado para poder decidir subir la foto conjuntamente con los datos del medicamento.
            /*
            if (imageBitmap != null) {
                uploadFile(imageBitmap)
                // goToPlanMedicineActivity(imageBitmap)
            }
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
    fun uploadFile(imageBitmap : Bitmap) : String{


        val storageRef = storage.reference
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageRef = storageRef.child(timeStamp+".jpg")
        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG,100, baos)
        val data = baos.toByteArray()
        var uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener{
            // Handle unsuccessful uploads

        }.addOnSuccessListener { taskSnapshot ->

            //uploadReference(timeStamp)
            //Toast.makeText(this, "HOLAAAAAAAAAAA onActivtyResult", Toast.LENGTH_SHORT).show()

        }
        return "$timeStamp.jpg"
    }

    private fun uploadReference(ref : String){
        val cloudFirestore = FirebaseFirestore.getInstance()

        var url = ref

        cloudFirestore.collection("users").document("testeo").set( hashMapOf("url" to url))
    }

    private fun sendAlarms() {
        checkFieldsAreFilled()
        if(timeInMillisList.size >= 1){
            val check = responsibleConsumption()

            if (!check && numDays.text.isNotEmpty()) {
                onSavePressed()
            }
            else {
                if(check && numDays.text.isNotEmpty()) {
                    setAlarms()
                    resetCalendar()
                    goHome()
                }
            }
        }
    }

    private fun refreshList() {
        if(timeInMillisList.size > 0)
            linearLayout6.addView(makeNewTextView(RandomUtils.hourFormatter(timeInMillisList[i]), i.toString()))
    }

    private fun refreshDate() {
        if (initialDate != null){
            textView8.text = "El dia de inicio es:  " + RandomUtils.dayFormatter(initialDate);
        }
    }

    private fun deleteView(){
        linearLayout6.removeAllViewsInLayout()
        //linearLayout6.height = ViewGroup.LayoutParams.WRAP_CONTENT
        linearLayout6.updateLayoutParams { height = ViewGroup.LayoutParams.WRAP_CONTENT }
    }






}