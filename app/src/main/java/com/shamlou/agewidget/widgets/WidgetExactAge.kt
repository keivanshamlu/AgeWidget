package com.shamlou.agewidget.widgets

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import android.widget.Toast
import androidx.annotation.CallSuper
import com.shamlou.agewidget.R
import com.shamlou.agewidget.base.BirthResource
import com.shamlou.agewidget.base.updateWidgets
import com.shamlou.agewidget.domain.UserBirthDomain
import com.shamlou.agewidget.manager.TimeManager
import com.shamlou.agewidget.usecases.UseCaseBirthCheckUserBirthCache
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


private const val ACTION_SCHEDULED_UPDATE = "com.shamlou.agewidget.SCHEDULED_UPDATE"

@AndroidEntryPoint
class WidgetExactAge : AppWidgetProvider() {

    @Inject
    lateinit var timeManager: TimeManager
    @Inject
    lateinit var useCaseBirthCheckUserBirthCache: UseCaseBirthCheckUserBirthCache

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {

        scheduleNextUpdate(context , timeManager.calculateNextMidnight())
        GlobalScope.launch {

            var remoteViews = RemoteViews(
                context.packageName,
                R.layout.widget_exact_age
            )
            useCaseBirthCheckUserBirthCache.invoke(Unit).collect { userBirthDateState ->

                when (userBirthDateState.status) {

                    BirthResource.Status.REGISTERED -> {

                        userBirthDateState.data?.let {
                            appWidgetManager.updateAppWidget(appWidgetIds, calculateAndShowUserAge(remoteViews, it, context))
                        } ?: run { remoteViews = showWeDontHaveYourBirthDate(remoteViews) }
                    }
                    BirthResource.Status.NOT_REGISTERED -> {

                        remoteViews = showWeDontHaveYourBirthDate(remoteViews)
                    }
                }
            }

            appWidgetManager.updateAppWidget(appWidgetIds, remoteViews)
        }
    }

    private fun calculateAndShowUserAge(
        remoteViews: RemoteViews,
        userBirthDomain: UserBirthDomain,
        context: Context
    ): RemoteViews {


        val calculateAge = timeManager.calculateAge(userBirthDomain.userBirthDomain.birthDateFormated)

        remoteViews.setViewVisibility(R.id.text_view_exact_age, View.VISIBLE)
        remoteViews.setViewVisibility(R.id.chronometer_age, View.VISIBLE)
        remoteViews.setViewVisibility(R.id.text_view_we_dont_have_your_birth_date, View.GONE)
        remoteViews.setViewVisibility(R.id.text_view_chronometer_helper, if(timeManager.isLessThanOneHour(calculateAge.second))View.VISIBLE else View.GONE)
        remoteViews.setTextViewText(R.id.text_view_exact_age, "${calculateAge.first.years}:${calculateAge.first.months}:${calculateAge.first.days}")
        remoteViews.setChronometerCountDown(R.id.chronometer_age, false)
        remoteViews.setChronometer(R.id.chronometer_age, SystemClock.elapsedRealtime() - calculateAge.second, null, true)
        if(timeManager.isLessThanOneHour(calculateAge.second))scheduleNextUpdate(context , timeManager.calculateNext1Am())
        return remoteViews
    }

    private fun showWeDontHaveYourBirthDate(remoteViews: RemoteViews): RemoteViews {

        remoteViews.setViewVisibility(R.id.text_view_exact_age, View.GONE)
        remoteViews.setViewVisibility(R.id.chronometer_age, View.GONE)
        remoteViews.setViewVisibility(R.id.text_view_we_dont_have_your_birth_date, View.VISIBLE)
        return remoteViews
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        if (intent.action == ACTION_SCHEDULED_UPDATE) {

            context.updateWidgets()
        }
        super.onReceive(context, intent)
    }

    private fun scheduleNextUpdate(context: Context , nextUpdateTime: Long) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, WidgetExactAge::class.java)
        intent.action = ACTION_SCHEDULED_UPDATE
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            nextUpdateTime,
            pendingIntent
        )
    }
}