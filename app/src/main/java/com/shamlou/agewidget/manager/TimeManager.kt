package com.shamlou.agewidget.manager

import com.shamlou.agewidget.domain.BirthDomain
import java.time.Period

interface TimeManager {

    //returns exact age period and chronometer time as Long
    fun calculateAge(formattedDate : String) : Pair<Period , Long>

    fun calculateNextMidnight() : Long

    fun calculateNext1Am() : Long

    fun isLessThanOneHour(timeAsMilis : Long) : Boolean

    fun formatDate(year: Int, month: Int, dayOfMonthyear: Int):String
}