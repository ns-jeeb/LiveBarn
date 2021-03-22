package com.example.livebarnapp

import android.content.Context
import org.hamcrest.CoreMatchers.`is`
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito.`when`

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Mock
    private lateinit var mockContext: Context

    @Test
    fun readStringFromContext_LocalizedString() {
        // Given a mocked Context injected into the object under test...
//        `when`(mockContext.getString(R.string.hello_word)).thenReturn(FAKE_STRING)
//        val myObjectUnderTest = SurfaceViewModel()
//
//        // ...when the string is returned from the object under test...
//        val result: String = myObjectUnderTest.getHelloWorldString()
//
//        // ...then the result should be the expected one.
//        assertThat(result, `is`(FAKE_STRING))
    }
}