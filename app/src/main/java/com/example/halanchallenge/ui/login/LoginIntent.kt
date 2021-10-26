package com.example.halanchallenge.ui.login

sealed class LoginIntent {
    data class Login(val username:String,val password:String):LoginIntent()
}