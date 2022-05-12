package com.grupo10.medicalthy

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_plan_medicine.*
import java.util.*

class PlanMedicineActivity : AppCompatActivity() {

    lateinit var notifications: Notifications
    var calendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan_medicine)

        setup()
    }

    private fun setup() {
        title = getString(R.string.planMedicineTitle)

        notifications = Notifications(this, Constants.ActivityRef.ShowShotsActivity.ordinal)
        notifications.createNotificationChannel() //Canal de notificaciones creado

        addDate.setOnClickListener {
            chooseInitialDate()
        }

        addHour.setOnClickListener {
            chooseInitialHour { timeInMillis -> notifications.setExactAlarm(timeInMillis) }
            resetCalendar()
        }

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
}