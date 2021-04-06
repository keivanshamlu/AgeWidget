package com.shamlou.agewidget.widgets

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.widget.RemoteViews
import com.shamlou.agewidget.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit


private const val ACTION_SCHEDULED_UPDATE = "com.shamlou.agewidget.SCHEDULED_UPDATE"
class WidgetExactAge : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {

        scheduleNextUpdate(context);
        val remoteViews = RemoteViews(
            context.packageName,
            R.layout.widget_exact_age
        )
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val today: LocalDate = LocalDate.now()
        val birthday: LocalDate = LocalDate.parse("1998-06-09", formatter)

        val p: Period = Period.between(birthday, today)

        val currentClockTime = TimeUnit.HOURS.toMillis(SimpleDateFormat("HH").format(Date()).toLong()) +
                TimeUnit.MINUTES.toMillis(SimpleDateFormat("mm").format(Date()).toLong()) +
                TimeUnit.SECONDS.toMillis(SimpleDateFormat("ss").format(Date()).toLong())


        remoteViews.setTextViewText(R.id.text_view_exact_age, "${p.years}:${p.months}:${p.days}")
        remoteViews.setChronometerCountDown(R.id.chronometer_age, false)
        remoteViews.setChronometer(R.id.chronometer_age,SystemClock.elapsedRealtime() - currentClockTime , null , true)
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews)

    }

    override fun onReceive(context: Context?, intent: Intent) {
        if (intent.action == ACTION_SCHEDULED_UPDATE) {
            val manager = AppWidgetManager.getInstance(context)
            val ids = manager.getAppWidgetIds(context?.let { ComponentName(it, WidgetExactAge::class.java) })
            onUpdate(context!!, manager, ids)
        }
        super.onReceive(context, intent)
    }

    private fun scheduleNextUpdate(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        // Substitute AppWidget for whatever you named your AppWidgetProvider subclass
        val intent = Intent(context, WidgetExactAge::class.java)
        intent.action = ACTION_SCHEDULED_UPDATE
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

        // Get a calendar instance for midnight tomorrow.
        val midnight = Calendar.getInstance()
        midnight[Calendar.HOUR_OF_DAY] = 0
        midnight[Calendar.MINUTE] = 0
        // Schedule one second after midnight, to be sure we are in the right day next time this
        // method is called.  Otherwise, we risk calling onUpdate multiple times within a few
        // milliseconds
        midnight[Calendar.SECOND] = 1
        midnight[Calendar.MILLISECOND] = 0
        midnight.add(Calendar.DAY_OF_YEAR, 1)

        // For API 19 and later, set may fire the intent a little later to save battery,
        // setExact ensures the intent goes off exactly at midnight.
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            midnight.timeInMillis,
            pendingIntent
        )
    }
}