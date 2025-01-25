package com.example.uaspam.service_api

import com.example.uaspam.model.Kategori
import com.example.uaspam.model.KategoriDetailResponse
import com.example.uaspam.model.KategoriResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface KategoriService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("kategori")
    suspend fun getKategori(): KategoriResponse

    @GET("kategori/{id}")
    suspend fun getKategoriById(@Path("id") id: Int): KategoriDetailResponse

    @POST("kategori/store")
    suspend fun insertKategori(@Body kategori: Kategori)

    @PUT("kategori/{id}")
    suspend fun updateKategori(@Path("id") id: Int, @Body kategori: Kategori)

    @DELETE("kategori/{id}")
    suspend fun deleteKategori(@Path("id") id: Int) : Response<Void>
}