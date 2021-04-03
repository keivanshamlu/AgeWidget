package com.shamlou.agewidget.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shamlou.agewidget.domain.UserBirthDomain

@Entity
data class UserBirth(
    @PrimaryKey(autoGenerate = true) val uid: Int = 1,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "birth_day") val birthDay: String?,
    @ColumnInfo(name = "birth_month") val birthMonth: String?,
    @ColumnInfo(name = "birth_year") val birthYear: String?,
    @ColumnInfo(name = "birth_date_formated") val birthDateFormated: String?
)

fun UserBirth.toDomain(): UserBirthDomain = this.let {
    UserBirthDomain(
        firstName ?: "",
        birthDay ?: "",
        birthMonth ?: "",
        birthYear ?: "",
        birthDateFormated ?: ""
    )
}