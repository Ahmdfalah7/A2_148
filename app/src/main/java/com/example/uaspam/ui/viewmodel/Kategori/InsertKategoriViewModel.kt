package com.example.uaspam.ui.viewmodel.Kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Kategori
import com.example.uaspam.repository.KategoriRepository
import kotlinx.coroutines.launch

class InsertKategoriViewModel(private val kategoriRepository: KategoriRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertKategoriUiState())
        private set

    fun updateInsertKategoriState(insertKategoriUiEvent: InsertKategoriUiEvent) {
        uiState = InsertKategoriUiState(insertKategoriUiEvent = insertKategoriUiEvent)
    }

    fun insertKategori() {
        viewModelScope.launch {
            try {
                kategoriRepository.insertKategori(uiState.insertKategoriUiEvent.toKategori())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertKategoriUiState(
    val insertKategoriUiEvent: InsertKategoriUiEvent = InsertKategoriUiEvent()
)

data class InsertKategoriUiEvent(
    val id_kategori: Int = 0,
    val nama_kategori: String = "",
    val deskripsi_kategori: String = ""
)

fun InsertKategoriUiEvent.toKategori(): Kategori = Kategori(
    id_kategori = id_kategori,
    nama_kategori = nama_kategori,
    deskripsi_kategori = deskripsi_kategori
)

fun Kategori.toInsertKategoriUiEvent(): InsertKategoriUiEvent = InsertKategoriUiEvent(
    id_kategori = id_kategori,
    nama_kategori = nama_kategori,
    deskripsi_kategori = deskripsi_kategori
)
