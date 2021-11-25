package com.web0zz.countdown.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import java.util.*

// TODO add to manifest
class LaunchDateAlarmAction : BroadcastReceiver() {
    override fun onReceive(p0: Context, p1: Intent) {
        // TODO find what to show when launch time is on
    }
}

class LaunchDateAlarmBuilder(
    private val appContext: Context
) {
    private val alarmManager = appContext.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

    private val setAlarmIntent = Intent(appContext, LaunchDateAlarmAction::class.java)

    fun setAlarm(launchHour: Int, launchMinute: Int) {
        val pendingIntentSetAlarm = PendingIntent.getBroadcast(
            appContext,
            0,
            setAlarmIntent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }
        )

        val launchDate: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, launchHour)
            set(Calendar.MINUTE, launchMinute)
        }

        alarmManager?.setExact(
            AlarmManager.RTC_WAKEUP,
            launchDate.timeInMillis,
            pendingIntentSetAlarm
        )
    }

    companion object {
        const val SET_ALARM = "SET_ALARM"
        const val NOT_SET_ALARM = "NOT_SET_ALARM"
    }
}
