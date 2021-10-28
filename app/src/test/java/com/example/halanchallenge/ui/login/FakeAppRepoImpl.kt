package com.example.halanchallenge.ui.login

import com.example.halanchallenge.model.AppResult
import com.example.halanchallenge.model.LoginResponse
import com.example.halanchallenge.repository.AppRepository
import com.example.halanchallenge.util.Utils
import retrofit2.Response

class FakeAppRepoImpl:AppRepository {
    override suspend fun getUserData(userName: String, password: String): AppResult<LoginResponse> {
        return Utils.handleSuccess(Response.success(
            200,
            LoginResponse()
        ))
    }
}