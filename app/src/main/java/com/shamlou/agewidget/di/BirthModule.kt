package com.shamlou.agewidget.di

import com.shamlou.agewidget.repository.birth.RepositoryBirth
import com.shamlou.agewidget.repository.birth.RepositoryBirthImpl
import com.shamlou.agewidget.usecases.UseCaseCheckUserBirthCache
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class BirthModule {

  @Binds
  abstract fun bindBirthRepository(
    birthRepositoryBirthImpl: RepositoryBirthImpl
  ): RepositoryBirth

}