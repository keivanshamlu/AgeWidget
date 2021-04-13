package com.shamlou.agewidget.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import com.shamlou.agewidget.MainCoroutineRule
import com.shamlou.agewidget.base.BirthResource
import com.shamlou.agewidget.base.Event
import com.shamlou.agewidget.domain.BirthDomain
import com.shamlou.agewidget.domain.UserBirthDomain
import com.shamlou.agewidget.getOrAwaitValue
import com.shamlou.agewidget.manager.TimeManager
import com.shamlou.agewidget.repository.birth.fakeUserBirthDomain
import com.shamlou.agewidget.usecases.UseCaseBirthCheckUserBirthCache
import com.shamlou.agewidget.usecases.UseCaseBirthInsertUserBirthCache
import com.shamlou.agewidget.usecases.UseCaseDeleteUserBirthCache
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.Matchers.`is`
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner::class)
class ViewModelMainTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var useCaseCheckUserBirthCache: UseCaseBirthCheckUserBirthCache
    @MockK
    lateinit var useCaseInsertUserBirthCache: UseCaseBirthInsertUserBirthCache
    @MockK
    lateinit var useCaseDeleteUserBirthCache: UseCaseDeleteUserBirthCache
    @MockK
    lateinit var timeManager: TimeManager

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var viewModelMain: ViewModelMain
    @Before
    fun setUp(){

        MockKAnnotations.init(this)
        every { useCaseCheckUserBirthCache.invoke(Unit) } returns flow {

            emit(BirthResource.loading(null))
            emit(BirthResource.registered(fakeUserBirthDomain))
        }
        viewModelMain = ViewModelMain(useCaseCheckUserBirthCache , useCaseInsertUserBirthCache , useCaseDeleteUserBirthCache , timeManager)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun checkUserBirthCacheUserRegistered() = mainCoroutineRule.runBlockingTest {

        //checkUserBirthCache() is in init{} so it will be called
        val userBirthCacheFirstState = viewModelMain.userBirthCache.getOrAwaitValue()
        val userBirthCacheSecondState = viewModelMain.userBirthCache.getOrAwaitValue()

        verify { useCaseCheckUserBirthCache.invoke(Unit) }
        assertThat(userBirthCacheFirstState.status , `is`(BirthResource.Status.LOADING))
        assertThat(userBirthCacheSecondState.status , `is`(BirthResource.Status.REGISTERED))
        assertThat(viewModelMain.registeredUser.getOrAwaitValue() , `is`(fakeUserBirthDomain))
        assertThat(viewModelMain.mainPageStates.getOrAwaitValue() , `is`(MainPageStates.REGISTERED))
        assertNull(viewModelMain.selectedBirthDate.getOrAwaitValue())
    }
    @Test
    @ExperimentalCoroutinesApi
    fun checkUserBirthCacheUserNotRegistered() = mainCoroutineRule.runBlockingTest {


        //we stub againg
        every { useCaseCheckUserBirthCache.invoke(Unit) } returns flow {

            emit(BirthResource.loading(null))
            emit(BirthResource.notRegistered(null))
        }
        //checkUserBirthCache() is in init{} so it will be called
        viewModelMain = ViewModelMain(useCaseCheckUserBirthCache , useCaseInsertUserBirthCache , useCaseDeleteUserBirthCache , timeManager)
        val userBirthCacheFirstState = viewModelMain.userBirthCache.getOrAwaitValue()
        val userBirthCacheSecondState = viewModelMain.userBirthCache.getOrAwaitValue()

        assertThat(userBirthCacheFirstState.status , `is`(BirthResource.Status.LOADING))
        assertThat(userBirthCacheSecondState.status , `is`(BirthResource.Status.NOT_REGISTERED))
        assertThat(viewModelMain.mainPageStates.getOrAwaitValue() , `is`(MainPageStates.DATE_NOT_SELECTED))
    }
    @Test
    fun setAppWidgetId() {

        //given
        val FakeAppWidgetId = 112233
        //when
        viewModelMain.setAppWidgetId(FakeAppWidgetId)
        //then
        assertThat(viewModelMain.appWidgetId.getOrAwaitValue() , `is`(FakeAppWidgetId))
    }
    @Test
    @Parameters(value = [
        "1998,6,9,1998-06-09",
        "137,12,30,137-12-30",
        "2020,0,30,2020-00-30",
        "0,0,0,0-00-00"
    ])
    fun setSelectedDateButWeAreNotSure(year: Int, month: Int, dayOfMonthyear: Int , formatted : String) {

        val result = BirthDomain(dayOfMonthyear.toString(), month.toString(), year.toString(), formatted)
        //when
        viewModelMain.setSelectedDateButWeAreNotSure(year, month, dayOfMonthyear)
        //then
        assertThat(viewModelMain.selectedBirthDate.getOrAwaitValue() , `is`(result))

    }

    @Test
    fun deleteSelectedDate(){

        //when
        viewModelMain.deleteSelectedDate()
        //then
        assertThat(viewModelMain.mainPageStates.getOrAwaitValue() , `is`(MainPageStates.DATE_NOT_SELECTED))
        assertNull(viewModelMain.mainPageStates.getOrAwaitValue())
    }
    @Test
    fun howToAddWidgetClicked() {

        //when
        viewModelMain.howToAddWidgetClicked()
        //then
        assertThat(viewModelMain.showSnackbarHelp.getOrAwaitValue() , `is`(Event(true)))
    }

    @Test
    fun deleteCache() = runBlocking {

        coEvery { useCaseDeleteUserBirthCache.invoke(Unit) } returns Unit
        //when
        viewModelMain.deleteCache()
        //then
        coVerify { useCaseDeleteUserBirthCache.invoke(Unit) }

        assertNull(viewModelMain.selectedBirthDate.getOrAwaitValue())
        assertThat(viewModelMain.closeKeyBoard.getOrAwaitValue() , `is`(Event(true)))
        assertThat(viewModelMain.updateWidget.getOrAwaitValue() , `is`(Event(true)))
        assertNull(viewModelMain.registeredUser.getOrAwaitValue())
        assertThat(viewModelMain.mainPageStates.getOrAwaitValue() , `is`(MainPageStates.DATE_NOT_SELECTED))
    }

}