package com.shamlou.agewidget.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shamlou.agewidget.MainCoroutineRule
import com.shamlou.agewidget.base.BirthResource
import com.shamlou.agewidget.db.user.UserDao
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
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UseCaseBirthCheckUserBirthCacheTest {

    lateinit var useCase : UseCaseBirthCheckUserBirthCache

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
        useCase = UseCaseBirthCheckUserBirthCache(repository)
    }

    @Test
    operator fun invoke() = mainCoroutineRule.runBlockingTest {

        every { repository.getUserBirth() } returns flow {
            emit(BirthResource.loading(null))
            emit(BirthResource.registered(fakeUserBirthDomain))
        }
        //when
        val result = useCase.invoke(Unit)

        //then
        val actual = mutableListOf<BirthResource<UserBirthDomain>>()
        result.take(2).collect {
            actual.add(it)
        }
        verify { repository.getUserBirth() }
        assertThat( actual[0].status , Matchers.equalTo(BirthResource.Status.LOADING))
        assertThat( actual[1].status , Matchers.equalTo(BirthResource.Status.REGISTERED))
        assertThat( actual[1].data , Matchers.equalTo(fakeUserBirthDomain))
    }

}