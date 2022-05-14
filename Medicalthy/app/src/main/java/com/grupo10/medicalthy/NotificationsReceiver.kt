package com.grupo10.medicalthy

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.util.*
import java.util.concurrent.TimeUnit


class NotificationsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val ref = intent.getIntExtra(Constants.CLASS_REF, 0)
        val notifications = Notifications(context,ref)

        val timeInMillis = intent.getLongExtra(Constants.EXACT_ALARM_TIME, 0L)

        when(intent.action){
            Constants.SET_EXACT_ALARM -> {
                notifications.generateNotification(context.getString(R.string.app_name), context.getString(R.string.notificationMessage), context.getString(R.string.notificationSubText))
            }

            Constants.SET_REPETITIVE_ALARM -> {
                val calendar = Calendar.getInstance().apply {
                    this.timeInMillis = timeInMillis + TimeUnit.DAYS.toMillis(7)
                }

                Notifications(context,ref).setRepetitiveAlarm(calendar.timeInMillis)
                notifications.generateNotification(context.getString(R.string.app_name), context.getString(R.string.notificationMessage), context.getString(R.string.notificationSubText))
            }
        }
    }
}