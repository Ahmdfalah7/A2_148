package com.example.uaspam.service_api

import com.example.uaspam.model.Buku
import com.example.uaspam.model.BukuDetailResponse
import com.example.uaspam.model.BukuResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BukuService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("buku")
    suspend fun getBuku(): BukuResponse

    @GET("buku/{id}")
    suspend fun getBukuById(@Path("id") id: Int): BukuDetailResponse

    @POST("buku/store")
    suspend fun insertBuku(@Body buku: Buku)

    @PUT("buku/{id}")
    suspend fun updateBuku(@Path("id") id: Int, @Body buku:Buku)

    @DELETE("buku/{id}")
    suspend fun deleteBuku(@Path("id") id: Int) : Response<Void>
}
