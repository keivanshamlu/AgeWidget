package com.shamlou.agewidget.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.shamlou.agewidget.base.BirthResource
import com.shamlou.agewidget.base.UseCaseBirthBase
import com.shamlou.agewidget.domain.UserBirthDomain
import com.shamlou.agewidget.repository.birth.RepositoryBirth
import javax.inject.Inject


class UseCaseBirthCheckUserBirthCache @Inject constructor(private val repository: RepositoryBirth) : UseCaseBirthBase<Unit, UserBirthDomain>() {
    override fun invoke(param: Unit): LiveData<BirthResource<UserBirthDomain>> {
        return Transformations.map(repository.getUserBirth()) {
            it // Place here your specific logic actions
        }
    }
}