package com.grupo10.medicalthy

import android.app.*
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*

//Enum con los distintos proveedores de autenticación
enum class ProviderType {
    BASIC //Email + password
    //Google
}
class HomeActivity : AppCompatActivity() {

    lateinit var notifications: Notifications

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setup()
    }

    private fun setup(){

        title = getString(R.string.homeTitle)

        notifications = Notifications(this, Constants.ActivityRef.HomeActivity.ordinal)
        notifications.createNotificationChannel() //Canal de notificaciones creado

        notifyButton.setOnClickListener {
            setAlarm{timeInMillis -> notifications.setExactAlarm(timeInMillis) }
        }

        btnAddMedicine.setOnClickListener {
            goToAddMedicine()
        }

        btnShowShots.setOnClickListener {
            goToShowSots()
        }
    }

    private fun goToAddMedicine(){
        val addMedicineIntent = Intent(this, AddMedicineActivity::class.java)
        startActivity(addMedicineIntent)
    }

    private fun goToShowSots(){
        val showShotsInIntent = Intent(this, ShowShotsActivity::class.java)
        startActivity(showShotsInIntent)
    }

    private fun setAlarm(timeInMillis: (Long) -> Unit){
        Calendar.getInstance().apply {
            this.set(Calendar.SECOND, 0)
            this.set(Calendar.MILLISECOND, 0)
            DatePickerDialog(
                this@HomeActivity,
                DialogFragment.STYLE_NO_INPUT, // Otra opción para elegir es 0.
                //Valores del año,mes y dia que eligirá el usuario
                { _, year, month, day ->
                    this.set(Calendar.YEAR, year)
                    this.set(Calendar.MONTH, month)
                    this.set(Calendar.DAY_OF_MONTH, day)

                    TimePickerDialog(
                        this@HomeActivity,
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
                },
                //Valores del año,mes y dia actual que tomará por defecto el date picker
                this.get(Calendar.YEAR),
                this.get(Calendar.MONTH),
                this.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

    }
}