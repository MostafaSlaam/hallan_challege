package com.example.halanchallenge.repository

import com.example.halanchallenge.model.AppResult
import com.example.halanchallenge.model.LoginResponse

interface AppRepository {
    suspend fun getUserData(userName:String,password:String):AppResult<LoginResponse>
}