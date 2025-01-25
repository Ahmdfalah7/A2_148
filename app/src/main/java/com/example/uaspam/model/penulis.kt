package com.example.uaspam.model

import kotlinx.serialization.Serializable

@Serializable
data class Penulis(
    val id_penulis: Int,
    val nama_penulis: String,
    val biografi: String,
    val kontak: String
)
@Serializable
data class PenulisResponse(
    val status: Boolean,
    val message: String,
    val data: List<Penulis>
)


@Serializable
data class PenulisDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Penulis
)
