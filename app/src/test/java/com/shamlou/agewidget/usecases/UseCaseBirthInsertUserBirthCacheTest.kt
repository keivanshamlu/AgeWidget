package com.shamlou.agewidget.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shamlou.agewidget.MainCoroutineRule
import com.shamlou.agewidget.base.BirthResource
import com.shamlou.agewidget.domain.UserBirthDomain
import com.shamlou.agewidget.repository.birth.RepositoryBirth
import com.shamlou.agewidget.repository.birth.fakeUserBirthDomain
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UseCaseBirthInsertUserBirthCacheTest {

    lateinit var useCase : UseCaseBirthInsertUserBirthCache

    @MockK
    lateinit var repository : RepositoryBirth

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp(){

        MockKAnnotations.init(this)
        useCase = UseCaseBirthInsertUserBirthCache(repository)
    }

    @Test
    operator fun invoke() = mainCoroutineRule.runBlockingTest {

        //given
        val param = fakeUserBirthDomain

        every { repository.setUserBirth(param) } returns Unit
        //when
        useCase.invoke(param)
        //then
        verify { repository.setUserBirth(param) }
    }
}