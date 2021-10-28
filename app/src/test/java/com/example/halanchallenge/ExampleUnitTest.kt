package com.example.halanchallenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.halanchallenge.ui.login.FakeAppRepoImpl
import com.example.halanchallenge.ui.login.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock

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

}