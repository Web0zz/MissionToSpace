package com.web0zz.countdown.launch_notification

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

// TODO temp launch notification info
data class LaunchCountdownData(
    val launchName: String,
    val launchDate: Long
)

class LaunchDayNotificationHandler @Inject constructor(
    private val appContext: Context,
    private val launchCountdownData: LaunchCountdownData
) {
    fun scheduleLaunchDateCountdownNotification(initialDelay: Long) {
        val launchData: Data = workDataOf(
            "LAUNCH_NAME" to launchCountdownData.launchName,
            "LAUNCH_DATE" to launchCountdownData.launchDate
        )

        val work = OneTimeWorkRequestBuilder<LaunchCountdownWorker>()
            .setInitialDelay(initialDelay, TimeUnit.DAYS)
            .addTag(LAUNCH_COUNTDOWN_TAG)
            .setInputData(launchData)
            .build()

        WorkManager.getInstance(appContext).enqueue(work)
    }

    private class LaunchCountdownWorker(
        appContext: Context,
        workerParams: WorkerParameters
    ) : Worker(appContext, workerParams) {
        @Inject
        private lateinit var launchDayNotificationBuilder: LaunchDayNotificationBuilder

        override fun doWork(): Result {
            val launchName = inputData.getString(LAUNCH_NAME) ?: LAUNCH_NAME
            val launchDate = inputData.getLong(LAUNCH_NAME, 0)

            return when (launchDayNotificationBuilder.showNotification(launchName, launchDate)) {
                LaunchDayNotificationBuilder.Companion.ActionResult.SUCCESS -> {
                    Result.success()
                }
                LaunchDayNotificationBuilder.Companion.ActionResult.FAILURE -> {
                    Result.failure()
                }
            }
        }
    }

    companion object {
        const val LAUNCH_NAME = "LAUNCH_NAME"
        const val LAUNCH_COUNTDOWN_TAG = "Launch Date Countdown"
    }
}