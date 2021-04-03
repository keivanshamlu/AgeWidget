package com.shamlou.agewidget.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shamlou.agewidget.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AnalyticsModule {

  @Provides
  fun provideAnalyticsService(
    @ApplicationContext applicationContext: Context
  ): RoomDatabase {
    return Room.databaseBuilder(
      applicationContext,
      AppDatabase::class.java, "birth-time-database"
    ).build()
  }
}