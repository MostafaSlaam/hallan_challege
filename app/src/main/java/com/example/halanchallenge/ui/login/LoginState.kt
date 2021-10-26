package com.example.halanchallenge.ui.login

import com.example.halanchallenge.model.LoginResponse

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class LoginDone(val response: LoginResponse) : LoginState()
    data class Error(val error: String?) : LoginState()
}