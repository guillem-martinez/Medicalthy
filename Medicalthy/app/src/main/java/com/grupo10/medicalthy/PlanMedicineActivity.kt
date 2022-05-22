package com.grupo10.medicalthy

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
import kotlin.math.abs


class PlanMedicineActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    lateinit var notifications: Notifications
    var calendar: Calendar = Calendar.getInstance()
    private var timeMillis: Long = 0
    private var initialDate: Long = 0
    private var timeInMillisList: MutableList<Long> = arrayListOf()
    private var nc : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan_medicine)
        
        nc = intent.getStringExtra("nc")

        setup()
    }

    private fun setMillis(timeInMillis : Long){
        this.timeMillis = timeInMillis
        timeInMillisList.add(timeInMillis)
    }

    private fun setup() {
        title = getString(R.string.planMedicineTitle)

        notifications = Notifications(this, Constants.ActivityRef.ShowShotsActivity.ordinal)
        notifications.createNotificationChannel() //Canal de notificaciones creado

        nc?.let { getMedicineName(it) }

        CodigoNacional.setText("El codigo nacional es: $nc")

        addDate.setOnClickListener {
            chooseInitialDate()
            Toast.makeText(this, RandomUtils.dateFormatter(initialDate), Toast.LENGTH_LONG).show()
        }

        addHour.setOnClickListener {
            chooseInitialHour { timeInMillis -> timeInMillisList.add(timeInMillis) }
        }


        saveButton.setOnClickListener {
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
            val data = hashMapOf(
                "nombre" to "Paracetamol",
                "codigo" to "989624.9"
            )

            db.collection("users").document("a123456@gmail.com").collection("Plan Prueba").document("Paracetamol").set(data)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "Document written")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "error adding document", e)
                }
        }
    }

    private fun getMedicineName(nationalCode: String): String? {
        val name : String? = ""

        db.collection("medicamentos").document(nationalCode).get().addOnSuccessListener {
            MedicineName.setText("El nombre del medicamento es: " + name)
        }

        return name
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
                },
                //Valores del año,mes y dia actual que tomará por defecto el date picker
                this.get(Calendar.YEAR),
                this.get(Calendar.MONTH),
                this.get(Calendar.DAY_OF_MONTH)
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
}