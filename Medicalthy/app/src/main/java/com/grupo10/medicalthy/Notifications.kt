package com.grupo10.medicalthy

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf


class Notifications(private val context: Context, private val ref: Int) {

    //Crear y construir el canal de notificaciones
    private val channelName = context.getString(R.string.channelName)
    private val channelId = context.getString(R.string.channelID)
    private val descriptionText = context.getString(R.string.channelDescription)
    private val importance = NotificationManager.IMPORTANCE_HIGH
    private val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
    private val audioAttr = AudioAttributes.Builder()
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .setUsage(AudioAttributes.USAGE_NOTIFICATION_EVENT)
        .build()

    private val largeIcon = BitmapFactory.decodeResource( // (2)
        context.resources,
        R.drawable.medicalthy
    )

    //Notification y Alarm Managers
    private val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val style = NotificationCompat.BigTextStyle().bigText(context.getString(R.string.notificationMessage))

    private val channel = NotificationChannel(channelId, channelName, importance).apply{
        this.description = descriptionText
        this.enableLights(true)
        this.enableVibration(true)
        this.lightColor = Color.RED
        this.vibrationPattern = longArrayOf(0, 500, 500, 500, 500)
        this.setShowBadge(true)
        this.canShowBadge()
        this.setSound(soundUri,audioAttr)
    }

    //Crea la notificación con el formato establecido
    fun generateNotification(title: String, message: String, subText: String){
        val notificationId = RandomUtils.getRandomInt()
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSubText(subText)
            .setSmallIcon(R.drawable.ic_circle_notifications)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setPriority(importance)
            .setLargeIcon(largeIcon)
            .setContentIntent(getPendingActivityIntent(notificationId,title,message))
            .setAutoCancel(true) //Elimina la notificación al hacer tap
            .setOngoing(true) // La notificación no se puede descartar por swipe
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSound(soundUri)
            .setColor(Color.RED)
            .setStyle(style)
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
    fun setRepetitiveAlarm(timeInMillis: Long, numDays: Int){
        setAlarm(
            timeInMillis,
            getPendingIntent(getIntent().apply {
                action = Constants.SET_REPETITIVE_ALARM
                putExtra(Constants.EXACT_ALARM_TIME, timeInMillis)
                putExtra(Constants.CLASS_REF, ref)
                putExtra(Constants.NUM_DAYS, numDays)
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
            getPendingIntent(RandomUtils.getRandomInt(), PendingIntent.FLAG_UPDATE_CURRENT)
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