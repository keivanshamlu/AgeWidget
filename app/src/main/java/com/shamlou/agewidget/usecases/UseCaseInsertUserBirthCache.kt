package com.shamlou.agewidget.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.shamlou.agewidget.base.BirthResource
import com.shamlou.agewidget.base.UseCaseBase
import com.shamlou.agewidget.domain.UserBirthDomain
import com.shamlou.agewidget.repository.birth.RepositoryBirth
import javax.inject.Inject


class UseCaseInsertUserBirthCache @Inject constructor(private val repository: RepositoryBirth) : UseCaseBase<UserBirthDomain, Unit>() {
    override fun invoke(param: UserBirthDomain): LiveData<BirthResource<Unit>> {
        return Transformations.map(repository.setUserBirth(param)) {
            it // Place here your specific logic actions
        }
    }
}