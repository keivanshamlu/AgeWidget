package com.shamlou.agewidget.repository.birth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.shamlou.agewidget.base.BirthResource
import com.shamlou.agewidget.base.Resource
import com.shamlou.agewidget.db.user.UserDao
import com.shamlou.agewidget.db.user.toDomain
import com.shamlou.agewidget.domain.UserBirthDomain
import com.shamlou.agewidget.domain.toRemote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryBirthImpl
@Inject constructor(private val userDao: UserDao) : RepositoryBirth {
    override fun getUserBirth(): LiveData<BirthResource<UserBirthDomain>> = liveData {

        emit(
            BirthResource.loading(null)
        )
        try {

            withContext(Dispatchers.IO) {
                val response = userDao.getUserBirth()
                emit(
                    response?.let {
                        BirthResource.registered(it.toDomain())
                    } ?: run {
                        BirthResource.notRegistered(null)
                    }
                )
            }
        } catch (exception: Throwable) {
            Log.d("exception", exception.message ?: "")
            emit(
                BirthResource.notRegistered(null)
            )
        }
    }

    override fun setUserBirth(userBirthDomain: UserBirthDomain) {

            try {

                userDao.insert(userBirthDomain.toRemote())

            } catch (exception: Throwable) {
                Log.d("exception", exception.message ?: "")
            }
        }
    override fun deleteUserBirth() {

            try {

                userDao.deleteRaw()
            } catch (exception: Throwable) {
                Log.d("exception", exception.message ?: "")
            }
        }
}