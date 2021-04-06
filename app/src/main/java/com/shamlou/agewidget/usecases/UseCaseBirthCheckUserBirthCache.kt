package com.shamlou.agewidget.usecases

import com.shamlou.agewidget.base.BirthResource
import com.shamlou.agewidget.base.UseCaseBirthBase
import com.shamlou.agewidget.domain.UserBirthDomain
import com.shamlou.agewidget.repository.birth.RepositoryBirth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class UseCaseBirthCheckUserBirthCache @Inject constructor(private val repository: RepositoryBirth) : UseCaseBirthBase<Unit, UserBirthDomain>() {
    override fun invoke(param: Unit): Flow<BirthResource<UserBirthDomain>> {
        return repository.getUserBirth().flowOn(Dispatchers.IO)
    }
}