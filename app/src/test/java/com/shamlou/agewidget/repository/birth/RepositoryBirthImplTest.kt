package com.shamlou.agewidget.repository.birth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shamlou.agewidget.MainCoroutineRule
import com.shamlou.agewidget.base.BirthResource
import com.shamlou.agewidget.db.user.UserBirth
import com.shamlou.agewidget.db.user.UserDao
import com.shamlou.agewidget.db.user.toDomain
import com.shamlou.agewidget.domain.UserBirthDomain
import com.shamlou.agewidget.domain.toRemote
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
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
import java.lang.Exception

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

        verify { userDao.getUserBirth() }
        Assert.assertThat( actual[0].status , equalTo(BirthResource.Status.LOADING))
        Assert.assertThat( actual[1].status , equalTo(BirthResource.Status.REGISTERED))
        Assert.assertThat( actual[1].data?.firstName , equalTo(fakeUserBirth.firstName))
        Assert.assertThat( actual[1].data?.userBirthDomain , equalTo(fakeUserBirth.toDomain().userBirthDomain))
    }
    @Test
    @ExperimentalCoroutinesApi
    fun getUserBirthReturnNull() = mainCoroutineRule.runBlockingTest {

        //given
        val result = repository.getUserBirth()

        //when
        every { userDao.getUserBirth() } returns null

        //then
        val actual = mutableListOf<BirthResource<UserBirthDomain>>()
        result.take(2).collect {
            actual.add(it)
        }
        Assert.assertThat( actual[0].status , equalTo(BirthResource.Status.LOADING))
        Assert.assertThat( actual[1].status, equalTo(BirthResource.Status.NOT_REGISTERED))
        Assert.assertNull( actual[1].data)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun getUserBirthThrowsException() = mainCoroutineRule.runBlockingTest {

        //given
        val result = repository.getUserBirth()

        //when
        every { userDao.getUserBirth() } throws Exception()

        //then
        val actual = mutableListOf<BirthResource<UserBirthDomain>>()
        result.take(2).collect {
            actual.add(it)
        }
        Assert.assertThat( actual[0].status , equalTo(BirthResource.Status.LOADING))
        Assert.assertThat( actual[1].status, equalTo(BirthResource.Status.NOT_REGISTERED))
        Assert.assertNull( actual[1].data)
    }

    @Test
    fun setUserBirth() {

        //when
        repository.setUserBirth(fakeUserBirthDomain)

        //then
        verify { userDao.insert(fakeUserBirthDomain.toRemote()) }
    }

    @Test
    fun deleteUserBirth() {

        //when
        repository.deleteUserBirth()

        //then
        verify { userDao.deleteRaw() }
    }
}