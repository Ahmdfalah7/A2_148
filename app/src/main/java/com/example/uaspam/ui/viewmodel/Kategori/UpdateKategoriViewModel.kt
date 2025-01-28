package com.example.uaspam.ui.viewmodel.Kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.repository.KategoriRepository
import kotlinx.coroutines.launch

class UpdateKategoriViewModel(
    savedStateHandle: SavedStateHandle,
    private val kategoriRepository: KategoriRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertKategoriUiState())
        private set

    private val _idKategori: Int = checkNotNull(savedStateHandle["id_kategori"])

    init {
        viewModelScope.launch {
            val kategori = kategoriRepository.getKategoriById(_idKategori)
            uiState = InsertKategoriUiState(insertKategoriUiEvent = kategori.toInsertKategoriUiEvent())
        }
    }

    fun updateInsertKategoriState(insertKategoriUiEvent: InsertKategoriUiEvent) {
        uiState = InsertKategoriUiState(insertKategoriUiEvent = insertKategoriUiEvent)
    }

    suspend fun updateKategori() {
        viewModelScope.launch {
            try {
                kategoriRepository.updateKategori(_idKategori, uiState.insertKategoriUiEvent.toKategori())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
