package com.grupo10.medicalthy

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_plan_medicine.*
import java.util.*


class PlanMedicineActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    lateinit var notifications: Notifications
    var calendar: Calendar = Calendar.getInstance()
    private var timeMillis: Long = 0
    private var nc = "658257.2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan_medicine)

        var extras = intent.extras
        var filename = ""

        if (extras != null) {
            val byteArray = intent.getByteArrayExtra("image")
            val bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
        }

        if (extras != null) {
            nc = extras.getString("nc").toString()
        }



        setup()
    }

    private fun setMillis(timeInMillis : Long){
        this.timeMillis = timeInMillis
    }

    private fun setup() {
        title = getString(R.string.planMedicineTitle)

        notifications = Notifications(this, Constants.ActivityRef.ShowShotsActivity.ordinal)
        notifications.createNotificationChannel() //Canal de notificaciones creado

        getMedicineName(nc)

        CodigoNacional.setText("El codigo nacional es: $nc")

        addDate.setOnClickListener {
            chooseInitialDate()
        }

        addHour.setOnClickListener {
            chooseInitialHour { timeInMillis -> setMillis(timeInMillis) }
        }

        hoursAtDay.setOnClickListener {
            responsibleConsumption()
        }

        saveButton.setOnClickListener {
            notifications.setExactAlarm(timeMillis)
            Toast.makeText(this, RandomUtils.dateFormatter(timeMillis), Toast.LENGTH_LONG).show()
            resetCalendar()
            goHome()
        }
    }

    private fun getMedicineName(nationalCode: String): String? {
        var name : String? = ""

        db.collection("medicamentos").document(nationalCode).get().addOnSuccessListener {


            MedicineName.setText("El nombre del medicamento es: " + name)


        }



        return name

    }

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
                },
                //Valores del año,mes y dia actual que tomará por defecto el date picker
                this.get(Calendar.YEAR),
                this.get(Calendar.MONTH),
                this.get(Calendar.DAY_OF_MONTH)
            ).apply {
                datePicker.minDate = (System.currentTimeMillis() - 1000)
            }.show()
        }
    }

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
        val homeIntent = Intent(this, HomeActivity::class.java)
        startActivity(homeIntent)
    }

    private fun responsibleConsumption() {
        if(hoursAtDay.text.toString().toInt() < 4 && hoursAtDay.text != null) {
            showAlert(getString(R.string.consumoResponsable))
        }
    }

    private fun showAlert(errorMessage : String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.responsibleConsumption))
        builder.setMessage(errorMessage)
        builder.setPositiveButton(getString(R.string.acceptMessage), null)
        val dialog : AlertDialog = builder.create()
        dialog.show()
    }
}