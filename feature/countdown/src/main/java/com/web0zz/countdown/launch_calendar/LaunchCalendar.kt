package com.web0zz.countdown.launch_calendar

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.CalendarContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LaunchCalendar(
    private val appContext: Context
) {
    private fun insertEvent(
        calID: Long,
        title: String,
        startMillis: Long,
        endMillis: Long
    ): Boolean {
        return try {
            // TODO add texts to resources
            val values = ContentValues().apply {
                put(CalendarContract.Events.DTSTART, startMillis)
                put(CalendarContract.Events.DTEND, endMillis)
                put(CalendarContract.Events.TITLE, title)
                put(CalendarContract.Events.DESCRIPTION, "todo")
                put(CalendarContract.Events.CALENDAR_ID, calID)
                // TODO get time zone by device
                put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles")
            }
            appContext.contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun insertEventInvoke(
        calID: Long,
        title: String,
        startMillis: Long,
        endMillis: Long,
        scope: CoroutineScope,
        onResult: (Boolean) -> Unit
    ) {
        scope.launch(Dispatchers.Main) {
            val deferred = async(Dispatchers.IO) {
                insertEvent(calID, title, startMillis, endMillis)
            }
            onResult(deferred.await())
        }
    }

    private fun updateEvent(
        eventID: Long,
        title: String?,
        startMillis: Long?,
        endMillis: Long?,
        description: String?
    ): Boolean {
        return try {
            // TODO add texts to resources
            val values = ContentValues().apply {
                startMillis?.let { put(CalendarContract.Events.DTSTART, startMillis) }
                endMillis?.let { put(CalendarContract.Events.DTEND, endMillis) }
                title?.let { put(CalendarContract.Events.TITLE, title) }
                description?.let { put(CalendarContract.Events.DESCRIPTION, "todo") }
            }
            val updateUri: Uri =
                ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID)
            appContext.contentResolver.update(updateUri, values, null, null)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun updateEventInvoke(
        calID: Long,
        title: String?,
        startMillis: Long?,
        endMillis: Long?,
        description: String?,
        scope: CoroutineScope,
        onResult: (Boolean) -> Unit
    ) {
        scope.launch(Dispatchers.Main) {
            val deferred = async(Dispatchers.IO) {
                updateEvent(calID, title, startMillis, endMillis, description)
            }
            onResult(deferred.await())
        }
    }
}