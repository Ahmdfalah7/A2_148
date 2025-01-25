package com.example.uaspam.service_api

import com.example.uaspam.model.Penerbit
import com.example.uaspam.model.PenerbitDetailResponse
import com.example.uaspam.model.PenerbitResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PenerbitService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("penerbit")
    suspend fun getPenerbit(): PenerbitResponse

    @GET("penerbit/{id}")
    suspend fun getPenerbitById(@Path("id") id: Int): PenerbitDetailResponse

    @POST("penerbit/store")
    suspend fun insertPenerbit(@Body penerbit: Penerbit)

    @PUT("penerbit/{id}")
    suspend fun updatePenerbit(@Path("id") id: Int, @Body penerbit: Penerbit)

    @DELETE("penerbit/{id}")
    suspend fun deletePenerbit(@Path("id") id: Int) : Response<Void>
}