package com.web0zz.countdown.notification

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast

class LaunchDateNotificationActionHandler(
    private val appContext: Context
) {
    private val setAlarmIntent = Intent(appContext, LaunchAlarmActionReceiver::class.java)

    fun setAlarmAction(): PendingIntent {
        setAlarmIntent.action = LaunchDateAlarmBuilder.SET_ALARM
        return PendingIntent.getBroadcast(
            appContext,
            0,
            setAlarmIntent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }
        )
    }

    fun notSetAlarmAction(): PendingIntent {
        setAlarmIntent.action = LaunchDateAlarmBuilder.NOT_SET_ALARM

        return PendingIntent.getBroadcast(
            appContext,
            0,
            setAlarmIntent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }
        )
    }
}

// TODO add to manifest
class LaunchAlarmActionReceiver : BroadcastReceiver() {
    lateinit var alarmBuilder: LaunchDateAlarmBuilder

    override fun onReceive(p0: Context, p1: Intent) {
        alarmBuilder = LaunchDateAlarmBuilder(p0)

        when (p1.action) {
            "SET_ALARM" -> setAlarm()
            "NOT_SET_ALARM" -> notSetAlarm(p0)
        }
    }

    private fun setAlarm() {
        alarmBuilder.setAlarm(0, 0)
    }

    private fun notSetAlarm(p0: Context) {
        Toast.makeText(p0, "Don't skip to launch", Toast.LENGTH_SHORT).show()
    }
}