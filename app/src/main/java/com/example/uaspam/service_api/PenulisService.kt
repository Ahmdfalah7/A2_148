package com.example.uaspam.service_api

import com.example.uaspam.model.Penulis
import com.example.uaspam.model.PenulisDetailResponse
import com.example.uaspam.model.PenulisResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PenulisService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("penulis")
    suspend fun getPenulis(): PenulisResponse

    @GET("penulis/{id}")
    suspend fun getPenulisById(@Path("id") id: Int): PenulisDetailResponse

    @POST("penulis/store")
    suspend fun insertPenulis(@Body penulis: Penulis)

    @PUT("penulis/{id}")
    suspend fun updatePenulis(@Path("id") id: Int, @Body penulis: Penulis)

    @DELETE("penulis/{id}")
    suspend fun deletePenulis(@Path("id") id: Int)  : Response<Void>
}