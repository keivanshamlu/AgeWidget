package com.shamlou.agewidget.ui

import org.hamcrest.Matchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized


@RunWith(Parameterized::class)
class KotlinParameterizedTest(private val paramOne: Int, private val paramTwo: String) {


    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
            arrayOf(1, "1"),         // First test:  (paramOne = 1, paramTwo = "I")
            arrayOf(1999, "1999") // Second test: (paramOne = 1999, paramTwo = "MCMXCIX")
        )
    }

    @Test
    fun shouldReturnExpectedRomanForArabic() {
        assertThat(paramOne.toString(), equalTo(paramTwo))
    }

}