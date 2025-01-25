package com.example.uaspam.model

import kotlinx.serialization.Serializable

@Serializable
data class Penerbit(
    val id_penerbit: Int,
    val nama_penerbit: String,
    val alamat_penerbit: String,
    val telepon_penerbit: String
)
