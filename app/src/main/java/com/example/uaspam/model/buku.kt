package com.example.uaspam.model

import kotlinx.serialization.Serializable

@Serializable
data class Buku (
    val id_buku: Int,
    val nama_buku: String,
    val deskripsiBuku: String,
    val tanggalTerbit: String,
    val statusBuku: String,
    val id_kategori: Int,
    val id_penulis: Int,
    val id_penerbit: Int,
)
@Serializable
data class BukuResponse(
    val status: Boolean,
    val message: String,
    val data: List<Buku>
)



