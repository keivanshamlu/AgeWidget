package com.shamlou.agewidget.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shamlou.agewidget.base.BirthResource
import com.shamlou.agewidget.base.Event
import com.shamlou.agewidget.domain.BirthDomain
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

    private val _appWidgetId = MutableLiveData<Int?>()
    val appWidgetId: LiveData<Int?> = _appWidgetId

    private val _selectedBirthDate = MutableLiveData<BirthDomain?>()
    val selectedBirthDate: LiveData<BirthDomain?> = _selectedBirthDate

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

    fun setSelectedDateButWeAreNotSure(year : Int, month : Int, dayOfMonthyear : Int){

        Log.d("selected date" , "$year-$month-$dayOfMonthyear")
        _selectedBirthDate.value = BirthDomain(dayOfMonthyear.toString() , month.toString() , year.toString() , "$year-$month-$dayOfMonthyear")
    }

    fun deleteSelectedDate(){

        _selectedBirthDate.value = null
    }
}