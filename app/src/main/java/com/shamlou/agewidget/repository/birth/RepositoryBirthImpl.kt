package com.shamlou.agewidget.repository.birth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.shamlou.agewidget.base.BirthResource
import com.shamlou.agewidget.db.user.UserDao
import com.shamlou.agewidget.db.user.toDomain
import com.shamlou.agewidget.domain.UserBirthDomain
import com.shamlou.agewidget.domain.toRemote
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
            Log.d("TESTEST1" , "success")
        } catch (exception: Throwable) {
            Log.d("TESTEST1" , exception.message?:"")
            emit(
                BirthResource.notRegistered(null)
            )
        }
    }

    override fun setUserBirth(userBirthDomain: UserBirthDomain): LiveData<BirthResource<Unit>>  = liveData {

        emit(
            BirthResource.loading(null)
        )
        try {

            userDao.insert(userBirthDomain.toRemote())

            Log.d("TESTEST" , "success")
        } catch (exception: Throwable) {
            Log.d("TESTEST" , exception.message?:"")
            emit(
                BirthResource.notRegistered(null)
            )
        }
    }
}