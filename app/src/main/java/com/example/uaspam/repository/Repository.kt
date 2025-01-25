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

interface PenerbitRepository {
    suspend fun getPenerbit(): PenerbitResponse
    suspend fun insertPenerbit(penerbit: Penerbit)
    suspend fun updatePenerbit(id: Int, penerbit: Penerbit)
    suspend fun deletePenerbit(id: Int)
    suspend fun getPenerbitById(id: Int): Penerbit
}

class NetworkPenerbitRepository(
    private val penerbitApiService: PenerbitService
) : PenerbitRepository {
    override suspend fun insertPenerbit(penerbit: Penerbit) {
        try {
            penerbitApiService.insertPenerbit(penerbit)
        } catch (e: Exception) {
            throw IOException("Failed to insert penerbit", e)
        }
    }

    override suspend fun updatePenerbit(id: Int, penerbit: Penerbit) {
        try {
            penerbitApiService.updatePenerbit(id, penerbit)
        } catch (e: Exception) {
            throw IOException("Failed to update penerbit", e)
        }
    }

    override suspend fun deletePenerbit(id: Int) {
        try {
            penerbitApiService.deletePenerbit(id)
        } catch (e: Exception) {
            throw IOException("Failed to delete penerbit", e)
        }
    }

    override suspend fun getPenerbit(): PenerbitResponse {
        return try {
            penerbitApiService.getPenerbit()
        } catch (e: Exception) {
            throw IOException("Failed to fetch penerbit", e)
        }
    }

    override suspend fun getPenerbitById(id: Int): Penerbit {
        return try {
            penerbitApiService.getPenerbitById(id).data
        } catch (e: Exception) {
            throw IOException("Failed to fetch penerbit by id", e)
        }
    }
}

interface PenulisRepository {
    suspend fun getPenulis(): PenulisResponse
    suspend fun insertPenulis(penulis: Penulis)
    suspend fun updatePenulis(id: Int, penulis: Penulis)
    suspend fun deletePenulis(id: Int)
    suspend fun getPenulisById(id: Int): Penulis
}


class NetworkPenulisRepository(
    private val penulisApiService: PenulisService
) : PenulisRepository {
    override suspend fun insertPenulis(penulis: Penulis) {
        penulisApiService.insertPenulis(penulis)
    }

    override suspend fun updatePenulis(id: Int, penulis: Penulis) {
        penulisApiService.updatePenulis(id, penulis)
    }

    override suspend fun deletePenulis(id: Int) {
        try {
            penulisApiService.deletePenulis(id)
        } catch (e: Exception) {
            throw IOException("Failed to delete penulis", e)
        }
    }

    override suspend fun getPenulis(): PenulisResponse {
        return try {
            penulisApiService.getPenulis() // Mengambil response dari API
        } catch (e: Exception) {
            throw IOException("Failed to fetch penulis", e)
        }
    }

    override suspend fun getPenulisById(id: Int): Penulis {
        return try {
            penulisApiService.getPenulisById(id).data // Mengambil penulis berdasarkan ID
        } catch (e: Exception) {
            throw IOException("Failed to fetch penulis by ID", e)
        }
    }
}
