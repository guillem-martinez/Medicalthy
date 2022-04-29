package com.grupo10.medicalthy

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf


class Notifications(private val context: Context, private val ref: Int) {

    //Crear y construir el canal de notificaciones
    private val channelName = R.string.channelName.toString()
    private val channelId = R.string.channelID.toString()
    private val descriptionText = R.string.channelDescription.toString()
    private val importance = NotificationManager.IMPORTANCE_HIGH

    private val channel = NotificationChannel(channelId, channelName, importance).apply{
        description = descriptionText
    }

    //Notification y Alarm Managers
    private val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    //Crea la notificación con el formato establecido
    fun generateNotification(title: String, message: String){
        val notificationId = RandomUtils.getRandomInt()
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSubText("Tomate la pastilla viejo decrépito")
            .setSmallIcon(R.drawable.medicalthy)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setPriority(importance)
            .setContentIntent(getPendingActivityIntent(notificationId,title,message))
            .build()

        with(NotificationManagerCompat.from(context)){
            notify(notificationId, notification)
        }
    }

    //Alarma para un dia y hora exacta - 1 vez
    fun setExactAlarm(timeInMillis : Long){
        setAlarm(
            timeInMillis,
            getPendingIntent(getIntent().apply {
                action = Constants.SET_EXACT_ALARM
                putExtra(Constants.EXACT_ALARM_TIME, timeInMillis)
                putExtra(Constants.CLASS_REF, ref)
            })
        )
    }

    //Alarma para un dia y hora exacta - X veces
    fun setRepetitiveAlarm(timeInMillis: Long){
        setAlarm(
            timeInMillis,
            getPendingIntent(getIntent().apply {
                action = Constants.SET_REPETITIVE_ALARM
                putExtra(Constants.EXACT_ALARM_TIME, timeInMillis)
                putExtra(Constants.CLASS_REF, ref)
            })
        )
    }


    fun setAlarm(timeInMillis: Long, pendingIntent: PendingIntent){
        alarmManager.let {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                pendingIntent
            )
        }
    }

    private fun getIntent() = Intent(context, NotificationsReceiver::class.java)

    private fun getActivityIntent(notificationId: Int, title: String, message: String): Intent{
        val contentIntent = Intent(context, Constants.classRefMap[ref]).apply {
            putExtras(
                bundleOf(
                    "notificationId" to notificationId,
                    "title" to title,
                    "message" to message
                )
            )
        }
        return contentIntent
    }

    private fun getPendingActivityIntent(notificationId: Int, title: String, message: String): PendingIntent{
        val contentPendingIntent: PendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(getActivityIntent(notificationId, title, message))
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        return contentPendingIntent
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun getPendingIntent(intent: Intent) = PendingIntent.getBroadcast(
        context,
        RandomUtils.getRandomInt(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    fun createNotificationChannel(){
        notificationManager.createNotificationChannel(channel)
    }
}