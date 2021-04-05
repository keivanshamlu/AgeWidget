package com.shamlou.agewidget.manager

import android.os.SystemClock
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit

class TimeManagerImpl : TimeManager {

    override fun calculateAge(formattedDate: String): Pair<Period , Long> {

        val today: LocalDate = LocalDate.now()
        val birthday: LocalDate = LocalDate.parse(formattedDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        val period = Period.between(birthday, today)

        val currentClockTime = TimeUnit.HOURS.toMillis(SimpleDateFormat("HH").format(Date()).toLong()) +
                TimeUnit.MINUTES.toMillis(SimpleDateFormat("mm").format(Date()).toLong()) +
                TimeUnit.SECONDS.toMillis(SimpleDateFormat("ss").format(Date()).toLong())

        return Pair(period , SystemClock.elapsedRealtime() - currentClockTime)
    }
}