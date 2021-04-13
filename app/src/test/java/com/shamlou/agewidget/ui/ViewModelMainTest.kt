package com.shamlou.agewidget.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shamlou.agewidget.MainCoroutineRule
import com.shamlou.agewidget.base.BirthResource
import com.shamlou.agewidget.base.Event
import com.shamlou.agewidget.getOrAwaitValue
import com.shamlou.agewidget.manager.TimeManager
import com.shamlou.agewidget.repository.birth.fakeUserBirthDomain
import com.shamlou.agewidget.usecases.UseCaseBirthCheckUserBirthCache
import com.shamlou.agewidget.usecases.UseCaseBirthInsertUserBirthCache
import com.shamlou.agewidget.usecases.UseCaseDeleteUserBirthCache
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.Matchers.`is`
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
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
    fun deleteSelectedDate() {

        //Given

        //when
        viewModelMain.deleteSelectedDate()
        //then
        assertThat(viewModelMain.mainPageStates.getOrAwaitValue() , `is`(MainPageStates.DATE_NOT_SELECTED))
    }
}