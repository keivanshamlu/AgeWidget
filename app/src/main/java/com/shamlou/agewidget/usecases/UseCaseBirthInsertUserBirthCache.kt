package com.shamlou.agewidget.usecases

import com.shamlou.agewidget.base.UseCaseBaseSuspend
import com.shamlou.agewidget.domain.UserBirthDomain
import com.shamlou.agewidget.repository.birth.RepositoryBirth
import javax.inject.Inject


class UseCaseBirthInsertUserBirthCache @Inject constructor(private val repository: RepositoryBirth) : UseCaseBaseSuspend<UserBirthDomain, Unit>() {
    override suspend fun invoke(param: UserBirthDomain) {
        repository.setUserBirth(param)
    }

}