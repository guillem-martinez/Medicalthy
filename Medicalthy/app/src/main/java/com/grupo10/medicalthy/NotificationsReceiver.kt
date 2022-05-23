package com.grupo10.medicalthy

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.util.*
import java.util.concurrent.TimeUnit


class NotificationsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val ref = intent.getIntExtra(Constants.CLASS_REF, 0)
        val cnPlan = intent.getStringExtra("cn_plan").toString()
        val user = intent.getStringExtra("user").toString()

        val notifications = Notifications(context,ref,cnPlan,user)

        val timeInMillis = intent.getLongExtra(Constants.EXACT_ALARM_TIME, 0L)

        when(intent.action){
            Constants.SET_EXACT_ALARM -> {
                notifications.generateNotification(context.getString(R.string.app_name), context.getString(R.string.notificationMessage), context.getString(R.string.notificationSubText))
            }

            Constants.SET_REPETITIVE_ALARM -> {
                var numDays = intent.getIntExtra(Constants.NUM_DAYS, 0)

                val calendar = Calendar.getInstance().apply {
                    this.timeInMillis = timeInMillis + TimeUnit.DAYS.toMillis(1)
                }

                if (numDays != 0) {

                    numDays--
                    notifications.setRepetitiveAlarm(calendar.timeInMillis, numDays)
                    notifications.generateNotification(context.getString(R.string.app_name), context.getString(R.string.notificationMessage), context.getString(R.string.notificationSubText))
                }
            }
        }
    }
}