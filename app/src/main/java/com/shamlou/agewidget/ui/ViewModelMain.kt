package com.shamlou.agewidget.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shamlou.agewidget.base.BirthResource
import com.shamlou.agewidget.base.Event
import com.shamlou.agewidget.domain.UserBirthDomain
import com.shamlou.agewidget.usecases.UseCaseCheckUserBirthCache
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModelMain
@Inject constructor(private val useCaseCheckUserBirthCache: UseCaseCheckUserBirthCache) : ViewModel() {

    private var userBirthCacheSource: LiveData<BirthResource<UserBirthDomain>> = MutableLiveData()
    private val _userBirthCache = MediatorLiveData<BirthResource<UserBirthDomain>>()
    val userBirthCache: LiveData<BirthResource<UserBirthDomain>> get() = _userBirthCache

    private val _userRegistered = MutableLiveData<Event<UserBirthDomain>>()
    val userRegistered: LiveData<Event<UserBirthDomain>> = _userRegistered

    private val _userNotRegistered = MutableLiveData<Event<Unit>>()
    val userNotRegistered: LiveData<Event<Unit>> = _userNotRegistered

    private val _appWidgetId = MutableLiveData<Int?>()
    val appWidgetId: LiveData<Int?> = _appWidgetId

    init {

        //check user birth at first
        checkUserBirthCache()
    }

    private fun checkUserBirthCache(){

        _userBirthCache.removeSource(userBirthCacheSource)
        userBirthCacheSource = useCaseCheckUserBirthCache(Unit)
        _userBirthCache.addSource(userBirthCacheSource) { birthSource ->
            _userBirthCache.value = birthSource

            when(birthSource.status){
                BirthResource.Status.REGISTERED -> {}
                BirthResource.Status.NOT_REGISTERED -> {}
                BirthResource.Status.LOADING -> {}
            }
        }
    }

    fun setAppWidgetId(appWidgetId : Int?){

        _appWidgetId.value = appWidgetId
    }
}