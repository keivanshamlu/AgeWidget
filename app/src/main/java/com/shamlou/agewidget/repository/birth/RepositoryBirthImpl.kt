package com.shamlou.agewidget.repository.birth

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.shamlou.agewidget.base.BirthResource
import com.shamlou.agewidget.db.UserDao
import com.shamlou.agewidget.db.entities.toDomain
import com.shamlou.agewidget.domain.UserBirthDomain
import javax.inject.Inject

class RepositoryBirthImpl
@Inject constructor(private val userDao: UserDao) : RepositoryBirth {
    override fun getUserBirth(): LiveData<BirthResource<UserBirthDomain>> = liveData {

        emit(
            BirthResource.loading(null)
        )
        try {

            val response = userDao.getUserBirth()
            emit(
                response?.let {
                    BirthResource.registered(it.toDomain())
                } ?: run {
                    BirthResource.notRegistered(null)
                }
            )
        } catch (exception: Throwable) {
            emit(
                BirthResource.notRegistered(null)
            )
        }
    }
}