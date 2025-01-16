package com.putragandad.regres.core.data.source.remote

import com.putragandad.regres.core.data.source.remote.response.ListUserResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getListUser(page: Int, perPage: Int) : ListUserResponse {
        return apiService.getListUser(page, perPage)
    }

}