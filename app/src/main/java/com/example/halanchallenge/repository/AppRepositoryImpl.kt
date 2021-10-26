package com.example.halanchallenge.repository

import android.content.Context
import com.example.halanchallenge.model.AppResult
import com.example.halanchallenge.model.LoginDataModel
import com.example.halanchallenge.model.LoginResponse
import com.example.halanchallenge.util.Utils.handleApiError
import com.example.halanchallenge.util.Utils.handleSuccess
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(val context:Context, val api: ApiClient):AppRepository {

    override suspend fun getUserData(userName:String,password:String): AppResult<LoginResponse> {
        return try {
            val response = api.getUserData(LoginDataModel(userName,password))
            if (response.isSuccessful) {
                //save the data
                response.body()?.let {
                }
                handleSuccess(response)
            } else {
                handleApiError(response)
            }
        } catch (e: Exception) {
            AppResult.Error(e)
        }
    }
}