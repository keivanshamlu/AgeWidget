package com.shamlou.agewidget.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserBirth(
    @PrimaryKey(autoGenerate = true) val uid: Int = 1,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "birth_day") val birthDay: String?,
    @ColumnInfo(name = "birth_month") val birthMonth: String?,
    @ColumnInfo(name = "birth_year") val birthYear: String?
)