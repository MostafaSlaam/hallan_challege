package com.example.halanchallenge.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.halanchallenge.model.AppResult
import com.example.halanchallenge.repository.AppRepository
import com.example.halanchallenge.repository.AppRepositoryImpl
import com.example.halanchallenge.util.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.math.MathContext.UNLIMITED

import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val repository: AppRepository) : ViewModel() {
    val userIntent = Channel<LoginIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state: StateFlow<LoginState>
        get() = _state

    var userName=MutableLiveData("")
    var password=MutableLiveData("")
    var userNameError=MutableLiveData<String?>(null)
    var passwordError=MutableLiveData<String?>(null)


    init {
        handleIntent()
    }

    fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is LoginIntent.Login -> login(it.username, it.password)

                }
            }
        }
    }

    private fun login(userName: String, password: String) {
        viewModelScope.launch {
            _state.value = LoginState.Loading
            val result = repository.getUserData(userName, password)
            _state.value = when (result) {
                is AppResult.Success -> {
                    LoginState.LoginDone(result.successData)
                }
                is AppResult.Error -> {
                    LoginState.Error(result.exception.message)
                }
            }
        }
    }
    fun checkData(): Boolean {
        if (userName.value!!.isEmpty()) {
            userNameError.value = "الاسم مطلوب"
            return false
        } else if (!Validator.validateUsername(userName.value!!) ){
            userNameError.value= "الاسم غير صحيح"
            return false
        } else
            userNameError.value = null

        if (password.value!!.isEmpty()) {
            passwordError.value = "كلمة السر مطلوبة"
            return false
        } else if (!Validator.validatePassword(password.value!!)) {
            passwordError.value = "كلمة السر غير صحيحه"
            return false
        } else
            passwordError.value = null

        return true


    }
}