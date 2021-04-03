package com.shamlou.agewidget.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shamlou.agewidget.db.entities.User

@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}