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
import com.shamlou.agewidget.manager.TimeManager
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


private const val ACTION_SCHEDULED_UPDATE = "com.shamlou.agewidget.SCHEDULED_UPDATE"
@AndroidEntryPoint
class WidgetExactAge : AppWidgetProvider() {

    @Inject lateinit var timeManager: TimeManager
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

        // For API 19 and later, set may fire the intent a little later to save battery,
        // setExact ensures the intent goes off exactly at midnight.
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            timeManager.calculateNextMidnight(),
            pendingIntent
        )
    }
}