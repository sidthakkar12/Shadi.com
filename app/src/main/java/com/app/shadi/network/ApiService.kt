package com.app.shadi.network

import com.app.shadi.data.User
import retrofit2.http.GET

interface ApiService {
    @GET("?results=100")
    suspend fun getUsers(): User
}