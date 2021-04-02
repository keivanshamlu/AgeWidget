package com.shamlou.agewidget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.SystemClock
import android.util.Log
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*

class WidgetExactAge : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {

        val remoteViews = RemoteViews(
            context.packageName,
            R.layout.widget_exact_age
        )
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val today: LocalDate = LocalDate.now()
        val birthday: LocalDate = LocalDate.parse("09-06-1998", formatter)

        val p: Period = Period.between(birthday, today)
        val dateFormat  = SimpleDateFormat("HH:mm:ss")
        val time = dateFormat.format(Date());

        remoteViews.setTextViewText(R.id.text_view_exact_age, "${p.years}:${p.months}:${p.days}:$time")
        remoteViews.setChronometerCountDown(R.id.chronometer_age , false)
        remoteViews.setChronometer(R.id.chronometer_age ,SystemClock.elapsedRealtime()-22000 , null , true)
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews)

    }
}