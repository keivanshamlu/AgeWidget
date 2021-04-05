package com.shamlou.agewidget.usecases

import com.shamlou.agewidget.base.UseCaseBaseSuspend
import com.shamlou.agewidget.repository.birth.RepositoryBirth
import javax.inject.Inject


class UseCaseDeleteUserBirthCache @Inject constructor(private val repository: RepositoryBirth) : UseCaseBaseSuspend<Unit, Unit>() {
    override suspend fun invoke(param: Unit) {
        repository.deleteUserBirth()
    }

}