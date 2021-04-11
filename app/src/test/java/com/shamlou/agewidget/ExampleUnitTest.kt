package com.shamlou.agewidget

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shamlou.agewidget.db.user.UserBirth
import com.shamlou.agewidget.db.user.UserDao
import com.shamlou.agewidget.repository.birth.RepositoryBirth
import com.shamlou.agewidget.repository.birth.RepositoryBirthImpl
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleUnitTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var userDao: UserDao

    private lateinit var repoBirth: RepositoryBirth


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repoBirth = RepositoryBirthImpl(userDao)
    }

    @Test
    fun addition_isCorrect() {

        every { userDao.getUserBirth() } returns UserBirth(1 , "fist" , "bithday" , "birthmonth" , "year" , "formatted")
    }
}