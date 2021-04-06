package com.shamlou.agewidget.repository.birth

import androidx.lifecycle.LiveData
import com.shamlou.agewidget.base.BirthResource
import com.shamlou.agewidget.base.Resource
import com.shamlou.agewidget.domain.UserBirthDomain
import kotlinx.coroutines.flow.Flow

interface RepositoryBirth {

    fun getUserBirth(): Flow<BirthResource<UserBirthDomain>>
    fun setUserBirth(userBirthDomain: UserBirthDomain)
    fun deleteUserBirth()
}