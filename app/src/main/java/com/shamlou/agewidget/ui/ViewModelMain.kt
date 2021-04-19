package com.shamlou.agewidget.ui

import androidx.lifecycle.*
import com.shamlou.agewidget.base.BirthResource
import com.shamlou.agewidget.base.Event
import com.shamlou.agewidget.domain.BirthDomain
import com.shamlou.agewidget.domain.UserBirthDomain
import com.shamlou.agewidget.manager.TimeManager
import com.shamlou.agewidget.usecases.UseCaseBirthCheckUserBirthCache
import com.shamlou.agewidget.usecases.UseCaseBirthInsertUserBirthCache
import com.shamlou.agewidget.usecases.UseCaseDeleteUserBirthCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Period
import javax.inject.Inject

@HiltViewModel
class ViewModelMain
@Inject constructor(
    private val useCaseCheckUserBirthCache: UseCaseBirthCheckUserBirthCache,
    private val useCaseInsertUserBirthCache: UseCaseBirthInsertUserBirthCache,
    private val useCaseDeleteUserBirthCache: UseCaseDeleteUserBirthCache,
    private val manager: TimeManager
) : ViewModel() {

    private var userBirthCacheSource: LiveData<BirthResource<UserBirthDomain>> = MutableLiveData()
    private val _userBirthCache = MediatorLiveData<BirthResource<UserBirthDomain>>()
    val userBirthCache: LiveData<BirthResource<UserBirthDomain>> get() = _userBirthCache

    private val _appWidgetId = MutableLiveData<Int?>()
    val appWidgetId: LiveData<Int?> = _appWidgetId

    private val _updateWidget = MutableLiveData<Event<Boolean>>()
    val updateWidget: LiveData<Event<Boolean>> = _updateWidget

    private val _resultOk = MutableLiveData<Event<Int>>()
    val resultOk: LiveData<Event<Int>> = _resultOk

    private val _registeredUser = MutableLiveData<UserBirthDomain?>()
    val registeredUser: LiveData<UserBirthDomain?> = _registeredUser

    private val _selectedBirthDate = MutableLiveData<BirthDomain?>()
    val selectedBirthDate: LiveData<BirthDomain?> = _selectedBirthDate

    private val _closeKeyBoard = MutableLiveData<Event<Boolean>>()
    val closeKeyBoard: LiveData<Event<Boolean>> = _closeKeyBoard

    private val _showSnackbarHelp = MutableLiveData<Event<Boolean>>()
    val showSnackbarHelp: LiveData<Event<Boolean>> = _showSnackbarHelp

    private val _calculatedAge = MutableLiveData<Pair<Period, Long>>()
    val calculatedAge: LiveData<Pair<Period, Long>> = _calculatedAge

    private val _mainPageStates = MutableLiveData<MainPageStates>()
    val mainPageStates: LiveData<MainPageStates> = _mainPageStates

    var enteredName: MutableLiveData<String> = MediatorLiveData()

    val nameIsValid: LiveData<Boolean> = Transformations.map(enteredName) {

        val nameIsValied = it.length >= 3
        handleNameChanged(nameIsValied)
        nameIsValied
    }
    val checkState: LiveData<Boolean> = Transformations.map(mainPageStates) {

        if (it == MainPageStates.REGISTERED) {
            registeredUser.value?.let { checkUserAge(it) }
            appWidgetId.value?.let { _resultOk.value = Event(it) }
            _updateWidget.value = Event(true)
        }
        true
    }

    init {

        //check user birth at first
        checkUserBirthCache()
    }

    private fun checkUserAge(userBirth: UserBirthDomain) {

        _calculatedAge.value = manager.calculateAge(userBirth.userBirthDomain.birthDateFormated)
    }

    private fun handleNameChanged(isValidNow: Boolean) {

        if (isValidNow) _mainPageStates.value = MainPageStates.NAME_VALIDATED
        else if (_mainPageStates.value == MainPageStates.NAME_VALIDATED)
            _mainPageStates.value = MainPageStates.DATE_CONFIRMED
    }

    private fun checkUserBirthCache(): Job = viewModelScope.launch {

        _userBirthCache.removeSource(userBirthCacheSource)
        userBirthCacheSource = useCaseCheckUserBirthCache(Unit).asLiveData()
        _userBirthCache.addSource(userBirthCacheSource) { birthSource ->
            _userBirthCache.value = birthSource

            when (birthSource.status) {
                BirthResource.Status.REGISTERED -> {
                    //configuration mode
                    appWidgetId.value?.let { _resultOk.value = Event(it) }
                    _registeredUser.value = birthSource.data
                    _selectedBirthDate.value = null
                    _mainPageStates.value = MainPageStates.REGISTERED
                }
                BirthResource.Status.NOT_REGISTERED -> {
                    _mainPageStates.value = MainPageStates.DATE_NOT_SELECTED
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


        _selectedBirthDate.value = BirthDomain(
            dayOfMonthyear.toString(),
            month.toString(),
            year.toString(),
            manager.formatDate(year, month, dayOfMonthyear)
        )
        _mainPageStates.value = MainPageStates.DATE_SELECTED
    }

    fun deleteSelectedDate() {

        _selectedBirthDate.value = null
        _mainPageStates.value = MainPageStates.DATE_NOT_SELECTED
    }

    fun howToAddWidgetClicked() {

        _showSnackbarHelp.value = Event(true)
    }

    fun deleteCache(): Job = viewModelScope.launch {

        _closeKeyBoard.value = Event(true)
        withContext(Dispatchers.IO) {

            useCaseDeleteUserBirthCache.invoke(Unit)
        }
        _selectedBirthDate.value = null
        _registeredUser.value = null
        _updateWidget.value = Event(true)
        _mainPageStates.value = MainPageStates.DATE_NOT_SELECTED
    }


    fun dateConfirmed() {

        _mainPageStates.value = if (nameIsValid.value == true) MainPageStates.NAME_VALIDATED else MainPageStates.DATE_CONFIRMED
    }

    fun letsGoButtonClicked(): Job = viewModelScope.launch {

        _closeKeyBoard.value = Event(true)
        selectedBirthDate.value?.let { birth ->
            UserBirthDomain(enteredName.value ?: "", birth).let { userBirth ->
                withContext(Dispatchers.IO) {

                    useCaseInsertUserBirthCache.invoke(userBirth)
                }
                _registeredUser.value = userBirth
                _selectedBirthDate.value = null
                _mainPageStates.value = MainPageStates.REGISTERED

            }
        }
    }
    fun updateDate(){

        _calculatedAge.value = registeredUser.value?.userBirthDomain?.birthDateFormated?.let { manager.calculateAge(it) }
    }
}

enum class MainPageStates {
    DATE_NOT_SELECTED,
    DATE_SELECTED,
    DATE_CONFIRMED,
    NAME_VALIDATED,
    REGISTERED
}