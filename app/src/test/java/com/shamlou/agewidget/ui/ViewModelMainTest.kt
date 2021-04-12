package com.shamlou.agewidget.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.hamcrest.Matchers.`is`
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ViewModelMainTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

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