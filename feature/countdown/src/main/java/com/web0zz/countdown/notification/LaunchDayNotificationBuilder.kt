package com.web0zz.countdown.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.web0zz.countdown.R
import javax.inject.Inject

class LaunchDayNotificationBuilder @Inject constructor(
    private val appContext: Context
) {
    private val launchDateNotificationActionHandler =
        LaunchDateNotificationActionHandler(appContext)
    private val builder = NotificationCompat.Builder(appContext, CHANNEL_ID)

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = appContext.getString(R.string.channel_name)
            val descriptionText = appContext.getString(R.string.channel_description)
            val importance =
                NotificationManager.IMPORTANCE_DEFAULT // TODO Check other importance settings

            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(launchName: String, launchDate: Long): ActionResult =
        try {
            createNotificationChannel()

            // TODO save notificationId and update it in case of delay of launch date
            with(NotificationManagerCompat.from(appContext)) {
                notify(
                    1,
                    builder
                        .setContentTitle("$launchName launch date")
                        .setContentText("will launch at $launchDate \n Set alarm to launch time")
                        .addAction(1, "No", launchDateNotificationActionHandler.notSetAlarmAction())
                        .addAction(1, "Yes", launchDateNotificationActionHandler.setAlarmAction())
                        .build()
                )
            }
            ActionResult.SUCCESS
        } catch (e: Exception) {
            ActionResult.FAILURE
        }

    companion object {
        const val CHANNEL_ID = "LaunchDay"

        enum class ActionResult {
            SUCCESS,
            FAILURE
        }
    }
}