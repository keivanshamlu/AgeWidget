package com.shamlou.agewidget.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shamlou.agewidget.MainCoroutineRule
import com.shamlou.agewidget.getOrAwaitValue
import com.shamlou.agewidget.manager.TimeManager
import com.shamlou.agewidget.usecases.UseCaseBirthCheckUserBirthCache
import com.shamlou.agewidget.usecases.UseCaseBirthInsertUserBirthCache
import com.shamlou.agewidget.usecases.UseCaseDeleteUserBirthCache
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
        viewModelMain = ViewModelMain(useCaseCheckUserBirthCache , useCaseInsertUserBirthCache , useCaseDeleteUserBirthCache , timeManager)
    }
    @Test
    fun getUserBirthCache() {

        assertThat(4 , `is`(4))
    }

    @Test
    fun getAppWidgetId() {
    }

    @Test
    fun getUpdateWidget() {
    }

    @Test
    fun getResultOk() {
    }

    @Test
    fun getResultCancel() {
    }

    @Test
    fun getRegisteredUser() {
    }

    @Test
    fun getSelectedBirthDate() {
    }

    @Test
    fun getCloseKeyBoard() {
    }

    @Test
    fun getShowSnackbarHelp() {
    }

    @Test
    fun getCalculatedAge() {
    }

    @Test
    fun getMainPageStates() {
    }

    @Test
    fun getEnteredName() {
    }

    @Test
    fun setEnteredName() {
    }

    @Test
    fun getNameIsValid() {
    }

    @Test
    fun getCheckState() {
    }

    @Test
    fun setAppWidgetId() {
    }

    @Test
    fun setSelectedDateButWeAreNotSure() {
    }

    @Test
    fun deleteSelectedDate() {

        //Given

        //when
        viewModelMain.deleteSelectedDate()
        //then
        assertThat(viewModelMain.mainPageStates.getOrAwaitValue() , `is`(MainPageStates.DATE_NOT_SELECTED))
    }

    @Test
    fun howToAddWidgetClicked() {
    }

    @Test
    fun deleteCache() {
    }

    @Test
    fun dateConfirmed() {
    }

    @Test
    fun letsGoButtonClicked() {
    }

    @Test
    fun updateDate() {
    }
}