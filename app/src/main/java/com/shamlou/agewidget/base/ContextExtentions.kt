package com.shamlou.agewidget.base

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.shamlou.agewidget.widgets.WidgetExactAge

fun Context.updateWidgets(){

    val appWidgetId = AppWidgetManager.getInstance(this).getAppWidgetIds(
        ComponentName(
            this,
            WidgetExactAge::class.java
        )
    )
    val intent = Intent(this, WidgetExactAge::class.java)
    intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetId)
    sendBroadcast(intent)
}