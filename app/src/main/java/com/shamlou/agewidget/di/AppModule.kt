package com.shamlou.agewidget.di

import android.content.Context
import androidx.room.Room
import com.shamlou.agewidget.db.AppDatabase
import com.shamlou.agewidget.db.user.UserDao
import com.shamlou.agewidget.repository.birth.RepositoryBirth
import com.shamlou.agewidget.repository.birth.RepositoryBirthImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

  @Provides
  fun provideAppDataBase(
    @ApplicationContext applicationContext: Context
  ): AppDatabase {
    return Room.databaseBuilder(
      applicationContext,
      AppDatabase::class.java, "birth-time-database"
    ).build()
  }

  @Provides
  fun provideAppDataBaseUserDao(
    dataBase : AppDatabase
  ): UserDao {
    return dataBase.userDao()
  }

  @Provides
  fun provideRepository(
    dao: UserDao
  ): RepositoryBirth {
    return RepositoryBirthImpl(dao)
  }
}