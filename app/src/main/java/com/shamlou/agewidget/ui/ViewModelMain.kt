package com.shamlou.agewidget.ui

import android.util.Log
import androidx.lifecycle.*
import com.shamlou.agewidget.base.BirthResource
import com.shamlou.agewidget.domain.BirthDomain
import com.shamlou.agewidget.domain.UserBirthDomain
import com.shamlou.agewidget.usecases.UseCaseCheckUserBirthCache
import com.shamlou.agewidget.usecases.UseCaseInsertUserBirthCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ViewModelMain
@Inject constructor(
    private val useCaseCheckUserBirthCache: UseCaseCheckUserBirthCache,
    private val useCaseInsertUserBirthCache: UseCaseInsertUserBirthCache
) : ViewModel() {

    private var userBirthCacheSource: LiveData<BirthResource<UserBirthDomain>> = MutableLiveData()
    private val _userBirthCache = MediatorLiveData<BirthResource<UserBirthDomain>>()
    val userBirthCache: LiveData<BirthResource<UserBirthDomain>> get() = _userBirthCache

    private val _appWidgetId = MutableLiveData<Int?>()
    val appWidgetId: LiveData<Int?> = _appWidgetId

    private val _selectedBirthDate = MutableLiveData<BirthDomain?>()
    val selectedBirthDate: LiveData<BirthDomain?> = _selectedBirthDate

    private val _notRegisteredStates = MutableLiveData<NotRegisteredStates>().apply {
        value = NotRegisteredStates.DATE_NOT_SELECTED
    }
    val notRegisteredStates: LiveData<NotRegisteredStates> = _notRegisteredStates

    var enteredName: MutableLiveData<String> = MediatorLiveData()

    val nameIsValid: LiveData<Boolean> = Transformations.map(enteredName) {

        val nameIsValied = it.length >= 3
        handleNameChanged(nameIsValied)
        nameIsValied
    }

    init {

        //check user birth at first
        checkUserBirthCache()
    }

    private fun handleNameChanged(isValidNow: Boolean) {

        if (isValidNow) _notRegisteredStates.value = NotRegisteredStates.NAME_VALIDATED
        else if (_notRegisteredStates.value == NotRegisteredStates.NAME_VALIDATED)
            _notRegisteredStates.value = NotRegisteredStates.DATE_CONFIRMED
    }

    private fun checkUserBirthCache(): Job = viewModelScope.launch {


        _userBirthCache.removeSource(userBirthCacheSource)
        withContext(Dispatchers.IO) {
            userBirthCacheSource = useCaseCheckUserBirthCache(Unit)
        }
        _userBirthCache.addSource(userBirthCacheSource) { birthSource ->
            _userBirthCache.value = birthSource

            when (birthSource.status) {
                BirthResource.Status.REGISTERED -> {
                    Log.d(
                        "REGISTERED",
                        "${birthSource.data?.firstName} - ${birthSource.data?.userBirthDomain?.birthDateFormated}"
                    )
                }
                BirthResource.Status.NOT_REGISTERED -> {
                }
                BirthResource.Status.LOADING -> {
                }
            }

        }
    }

    fun setAppWidgetId(appWidgetId: Int?) {

        _appWidgetId.value = appWidgetId
    }

    fun setSelectedDateButWeAreNotSure(year: Int, month: Int, dayOfMonthyear: Int) {

        Log.d("selected date", "$year-$month-$dayOfMonthyear")
        _selectedBirthDate.value = BirthDomain(
            dayOfMonthyear.toString(),
            month.toString(),
            year.toString(),
            "$year-$month-$dayOfMonthyear"
        )
        _notRegisteredStates.value = NotRegisteredStates.DATE_SELECTED
    }

    fun deleteSelectedDate() {

        _selectedBirthDate.value = null
        _notRegisteredStates.value = NotRegisteredStates.DATE_NOT_SELECTED
    }

    fun dateConfirmed() {

        _notRegisteredStates.value =
            if (nameIsValid.value == true) NotRegisteredStates.NAME_VALIDATED else NotRegisteredStates.DATE_CONFIRMED
    }

    fun letsGoButtonClicked(): Job = viewModelScope.launch {

        selectedBirthDate.value?.let {
            withContext(Dispatchers.IO) {
                useCaseInsertUserBirthCache.invoke(UserBirthDomain(enteredName.value ?: "", it))
            }
        }
    }
}

enum class NotRegisteredStates {
    DATE_NOT_SELECTED,
    DATE_SELECTED,
    DATE_CONFIRMED,
    NAME_VALIDATED
}