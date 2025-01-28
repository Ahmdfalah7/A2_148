package com.example.uaspam.ui.viewmodel.Buku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Buku
import com.example.uaspam.model.Kategori
import com.example.uaspam.model.KategoriResponse
import com.example.uaspam.model.Penerbit
import com.example.uaspam.model.PenerbitResponse
import com.example.uaspam.model.Penulis
import com.example.uaspam.model.PenulisResponse
import com.example.uaspam.repository.BukuRepository
import com.example.uaspam.repository.KategoriRepository
import com.example.uaspam.repository.PenerbitRepository
import com.example.uaspam.repository.PenulisRepository
import kotlinx.coroutines.launch

class InsertBukuViewModel(
    private val bukuRepository: BukuRepository,
    private val kategoriRepository: KategoriRepository,
    private val penerbitRepository: PenerbitRepository,
    private val penulisRepository: PenulisRepository,
) : ViewModel() {
    var uiState by mutableStateOf(InsertBukuUiState())
        private set

    init {
        loadDropdownOptions()
    }

    private fun loadDropdownOptions() {
        viewModelScope.launch {
            try {
                val kategoriResponse: KategoriResponse = kategoriRepository.getKategori()
                val kategoriList: List<Kategori> = kategoriResponse.data


                val penulisResponse: PenulisResponse = penulisRepository.getPenulis()
                val penulisList: List<Penulis> = penulisResponse.data

                val penerbitResponse: PenerbitResponse = penerbitRepository.getPenerbit()
                val penerbitList: List<Penerbit> = penerbitResponse.data

                uiState = uiState.copy(
                    kategoriOptions = kategoriList.map { it.toDropdownOptionKg() },
                    penulisOptions = penulisList.map { it.toDropdownOptionPs() },
                    penerbitOptions = penerbitList.map { it.toDropdownOptionPn() }
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertBukuState(insertBukuUiEvent: InsertBukuUiEvent) {
        uiState = uiState.copy(insertBukuUiEvent = insertBukuUiEvent)
    }

    fun insertBuku() {
        viewModelScope.launch {
            try {
                bukuRepository.insertBuku(uiState.insertBukuUiEvent.toBuku())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

    data class InsertBukuUiState(
        val insertBukuUiEvent: InsertBukuUiEvent = InsertBukuUiEvent(),
        val kategoriOptions: List<DropdownOptionKg> = emptyList(),
        val penulisOptions: List<DropdownOptionPs> = emptyList(),
        val penerbitOptions: List<DropdownOptionPn> = emptyList(),
    )

    data class InsertBukuUiEvent(
        val id_buku: Int = 0,
        val nama_buku: String = "",
        val deskripsi_buku: String = "",
        val tanggal_terbit: String = "",
        val status_buku: String = "",
        val id_kategori: Int = 0,
        val id_penulis: Int = 0,
        val id_penerbit: Int = 0,
    )

    fun InsertBukuUiEvent.toBuku(): Buku = Buku(
        id_buku = id_buku,
        nama_buku = nama_buku,
        deskripsi_buku = deskripsi_buku,
        tanggal_terbit = tanggal_terbit,
        status_buku = status_buku,
        id_kategori = id_kategori,
        id_penulis = id_penulis,
        id_penerbit = id_penerbit,
    )

    fun Buku.toInsertBukuUiEvent(): InsertBukuUiEvent = InsertBukuUiEvent(
        id_buku = id_buku,
        nama_buku = nama_buku,
        deskripsi_buku = deskripsi_buku,
        tanggal_terbit = tanggal_terbit,
        status_buku = status_buku,
        id_kategori = id_kategori,
        id_penerbit = id_penerbit,
        id_penulis = id_penulis
    )

    data class DropdownOptionKg(
        val id_kategori: Int,
        val label: String,
    )

    data class DropdownOptionPs(
        val id_penulis: Int,
        val label: String,
    )

    data class DropdownOptionPn(
        val id_penerbit: Int,
        val label: String,
    )

    fun Kategori.toDropdownOptionKg() = DropdownOptionKg(
        id_kategori = id_kategori,
        label = nama_kategori,
    )

    fun Penulis.toDropdownOptionPs() = DropdownOptionPs(
        id_penulis = id_penulis,
        label = nama_penulis,
    )

    fun Penerbit.toDropdownOptionPn() = DropdownOptionPn(
        id_penerbit = id_penerbit,
        label = nama_penerbit,
    )