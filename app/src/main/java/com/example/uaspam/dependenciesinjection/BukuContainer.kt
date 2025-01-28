package com.example.uaspam.dependenciesinjection

import com.example.uaspam.repository.BukuRepository
import com.example.uaspam.repository.KategoriRepository
import com.example.uaspam.repository.NetworkBukuRepository
import com.example.uaspam.repository.NetworkKategoriRepository
import com.example.uaspam.repository.NetworkPenerbitRepository
import com.example.uaspam.repository.NetworkPenulisRepository
import com.example.uaspam.repository.PenerbitRepository
import com.example.uaspam.repository.PenulisRepository
import com.example.uaspam.service_api.BukuService
import com.example.uaspam.service_api.KategoriService
import com.example.uaspam.service_api.PenerbitService
import com.example.uaspam.service_api.PenulisService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val bukuRepository: BukuRepository
    val kategoriRepository: KategoriRepository
    val penerbitRepository: PenerbitRepository
    val penulisRepository: PenulisRepository
}

class BukuContainer : AppContainer {
    private val baseUrl = "http://10.0.2.2:3000/api/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val bukuService: BukuService by lazy {
        retrofit.create(BukuService::class.java)
    }

    private val kategoriService: KategoriService by lazy {
        retrofit.create(KategoriService::class.java)
    }

    private val penerbitService: PenerbitService by lazy {
        retrofit.create(PenerbitService::class.java)
    }

    private val penulisService: PenulisService by lazy {
        retrofit.create(PenulisService::class.java)
    }

    override val bukuRepository: BukuRepository by lazy {
        NetworkBukuRepository(bukuService)
    }

    override val kategoriRepository: KategoriRepository by lazy {
        NetworkKategoriRepository(kategoriService)
    }

    override val penerbitRepository: PenerbitRepository by lazy {
        NetworkPenerbitRepository(penerbitService)
    }

    override val penulisRepository: PenulisRepository by lazy {
        NetworkPenulisRepository(penulisService)
    }
}
