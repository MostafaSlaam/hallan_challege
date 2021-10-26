package com.example.halanchallenge.repository

import com.example.halanchallenge.model.LoginDataModel
import com.example.halanchallenge.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiClient {
    @POST(EndPoints.LOGIN_API)
    suspend fun getUserData(@Body loginDataModel: LoginDataModel):Response<LoginResponse>
}