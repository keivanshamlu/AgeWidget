package com.shamlou.agewidget.repository.birth

import androidx.lifecycle.LiveData
import com.shamlou.agewidget.base.BirthResource
import com.shamlou.agewidget.base.Resource
import com.shamlou.agewidget.domain.UserBirthDomain

interface RepositoryBirth {

    fun getUserBirth(): LiveData<BirthResource<UserBirthDomain>>
    fun setUserBirth(userBirthDomain: UserBirthDomain)
}