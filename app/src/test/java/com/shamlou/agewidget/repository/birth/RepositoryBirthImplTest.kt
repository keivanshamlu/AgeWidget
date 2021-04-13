package com.shamlou.agewidget.repository.birth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shamlou.agewidget.MainCoroutineRule
import com.shamlou.agewidget.base.BirthResource
import com.shamlou.agewidget.db.user.UserBirth
import com.shamlou.agewidget.db.user.UserDao
import com.shamlou.agewidget.domain.UserBirthDomain
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.Matchers.*
import org.junit.Assert
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RepositoryBirthImplTest {

    lateinit var repository : RepositoryBirth

    @MockK
    lateinit var userDao: UserDao

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp(){

        MockKAnnotations.init(this)
        repository = RepositoryBirthImpl(userDao)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun getUserBirthSuccess() = mainCoroutineRule.runBlockingTest {

        //given
        val result = repository.getUserBirth()

        //when
        every { userDao.getUserBirth() } returns fakeUserBirth

        //then
        val actual = mutableListOf<BirthResource<UserBirthDomain>>()
        result.take(2).collect {
            actual.add(it)
        }
        Assert.assertThat( actual[0].status , equalTo(BirthResource.Status.LOADING))
        Assert.assertThat( actual[1].status , equalTo(BirthResource.Status.REGISTERED))
        Assert.assertThat( actual[1].data?.firstName , equalTo(fakeUserBirth.firstName))
    }
    @Test
    @ExperimentalCoroutinesApi
    fun getUserBirthFailure() = mainCoroutineRule.runBlockingTest {

        //given
        val result = repository.getUserBirth()

        //when
        every { userDao.getUserBirth() } returns null

        //then
        val actual = mutableListOf<BirthResource.Status>()
        result.take(2).collect {
            actual.add(it.status)
        }
        Assert.assertThat( actual[0] , equalTo(BirthResource.Status.LOADING))
        Assert.assertThat( actual[1] , equalTo(BirthResource.Status.NOT_REGISTERED))
    }

    @Test
    fun setUserBirth() {
    }

    @Test
    fun deleteUserBirth() {
    }
}