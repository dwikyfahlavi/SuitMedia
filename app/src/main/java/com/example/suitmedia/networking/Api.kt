package com.example.suitmedia.networking

import com.example.suitmedia.model.UsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface Api {
    @GET("users")
    fun getUsers(
        @QueryMap parameters: HashMap<String, String>
    ): Call<UsersResponse>
}