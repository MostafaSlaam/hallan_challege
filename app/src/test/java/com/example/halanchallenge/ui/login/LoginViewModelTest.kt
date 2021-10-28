package com.example.halanchallenge.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.core.IsEqual
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class LoginViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = LoginViewModel(FakeAppRepoImpl())
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }
    @Test
    fun checkInputData()
    {


        val result1 = viewModel.checkData()
        assertEquals(result1, false)

        viewModel.userName.value="mostafa22"
        viewModel.password.value="1122334455"
        val result2 = viewModel.checkData()
        assertEquals(result2, true)

        viewModel.userName.value=""
        viewModel.password.value="1122334455"
        val result3 = viewModel.checkData()
        assertEquals(result3, false)

        viewModel.userName.value="mostafa2020"
        viewModel.password.value=""
        val result4= viewModel.checkData()
        assertEquals(result4, false)

        viewModel.userName.value="mos"
        viewModel.password.value="1122334455"
        val result5= viewModel.checkData()
        assertEquals(result5, false)

        viewModel.userName.value="mostafa2018"
        viewModel.password.value="11"
        val result6= viewModel.checkData()
        assertEquals(result6, false)

    }
    @Test
    fun checkErrorMessages()
    {

        viewModel.checkData()
        assertEquals(viewModel.userNameError.value, "الاسم مطلوب")
        assertEquals(viewModel.passwordError.value, null)

        viewModel.userName.value="mostafa22"
        viewModel.password.value="1122334455"
        viewModel.checkData()
        assertEquals(viewModel.userNameError.value, null)
        assertEquals(viewModel.passwordError.value, null)


        viewModel.userName.value="mostafa2020"
        viewModel.password.value=""
        viewModel.checkData()
        assertEquals(viewModel.userNameError.value, null)
        assertEquals(viewModel.passwordError.value, "كلمة السر مطلوبة")

        viewModel.userName.value="mos"
        viewModel.password.value="1122334455"
         viewModel.checkData()
        assertEquals(viewModel.userNameError.value,  "الاسم غير صحيح")
        assertEquals(viewModel.passwordError.value, "كلمة السر مطلوبة")

        viewModel.userName.value="mostafa2018"
        viewModel.password.value="11"
         viewModel.checkData()
        assertEquals(viewModel.userNameError.value,  null)
        assertEquals(viewModel.passwordError.value, "كلمة السر غير صحيحه")
    }




}