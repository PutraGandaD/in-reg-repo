package com.putragandad.regres.core.data.source.remote

import com.putragandad.regres.core.data.source.remote.response.ListUserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getListUser(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): ListUserResponse
}