package com.shamlou.agewidget.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shamlou.agewidget.db.entities.UserBirth

@Database(entities = [UserBirth::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}