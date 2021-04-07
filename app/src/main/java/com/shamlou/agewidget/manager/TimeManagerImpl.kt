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

        return Pair(period , currentClockTime)
    }

    override fun calculateNextMidnight(): Long {

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

        return midnight.timeInMillis
    }

}