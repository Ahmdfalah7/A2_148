package com.example.uaspam.repository

import com.example.uaspam.model.Buku
import com.example.uaspam.model.BukuResponse
import com.example.uaspam.model.Kategori
import com.example.uaspam.model.KategoriResponse
import com.example.uaspam.model.Penerbit
import com.example.uaspam.model.PenerbitResponse
import com.example.uaspam.model.Penulis
import com.example.uaspam.model.PenulisResponse
import com.example.uaspam.service_api.BukuService
import com.example.uaspam.service_api.KategoriService
import com.example.uaspam.service_api.PenerbitService
import com.example.uaspam.service_api.PenulisService
import java.io.IOException

interface BukuRepository {
    suspend fun getBuku(): BukuResponse
    suspend fun insertBuku(buku: Buku)
    suspend fun updateBuku(id: Int, buku: Buku)
    suspend fun deleteBuku(id: Int)
    suspend fun getBukuById(id: Int): Buku
}

class NetworkBukuRepository(
    private val bukuApiService: BukuService
) : BukuRepository {
    override suspend fun insertBuku(buku: Buku) {
        bukuApiService.insertBuku(buku)
    }

    override suspend fun updateBuku(id: Int, buku: Buku) {
        bukuApiService.updateBuku(id, buku)
    }

    override suspend fun deleteBuku(id: Int) {
        try {
            bukuApiService.deleteBuku(id)
        } catch (e: Exception) {
            throw IOException("Failed to delete buku", e)
        }
    }

    override suspend fun getBuku(): BukuResponse {
        return bukuApiService.getBuku()
    }

    override suspend fun getBukuById(id: Int): Buku {
        return bukuApiService.getBukuById(id).data
    }
}

interface KategoriRepository {
    suspend fun getKategori(): KategoriResponse
    suspend fun insertKategori(kategori: Kategori)
    suspend fun updateKategori(id: Int, kategori: Kategori)
    suspend fun deleteKategori(id: Int)
    suspend fun getKategoriById(id: Int): Kategori
}

class NetworkKategoriRepository(
    private val kategoriApiService: KategoriService
) : KategoriRepository {
    override suspend fun insertKategori(kategori: Kategori) {
        kategoriApiService.insertKategori(kategori)
    }

    override suspend fun updateKategori(id: Int, kategori: Kategori) {
        kategoriApiService.updateKategori(id, kategori)
    }

    override suspend fun deleteKategori(id: Int) {
        try {
            kategoriApiService.deleteKategori(id)
        } catch (e: Exception) {
            throw IOException("Failed to delete kategori", e)
        }
    }

    override suspend fun getKategori(): KategoriResponse {
        return kategoriApiService.getKategori()
    }

    override suspend fun getKategoriById(id: Int): Kategori {
        return kategoriApiService.getKategoriById(id).data
    }
}

