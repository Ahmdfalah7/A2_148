package com.example.uaspam.ui.viewmodel.Buku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.repository.BukuRepository
import kotlinx.coroutines.launch

class UpdateBukuViewModel(
    savedStateHandle: SavedStateHandle,
    private val bukuRepository: BukuRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertBukuUiState())
        private set

    // Validasi dan konversi id_buku
    private val _idBuku: Int = savedStateHandle.get<String>("id_buku")?.toIntOrNull()
        ?: throw IllegalArgumentException("id_buku is missing or invalid in SavedStateHandle")

    init {
        viewModelScope.launch {
            try {
                val buku = bukuRepository.getBukuById(_idBuku)
                uiState = InsertBukuUiState(insertBukuUiEvent = buku.toInsertBukuUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertBukuState(insertBukuUiEvent: InsertBukuUiEvent) {
        uiState = InsertBukuUiState(insertBukuUiEvent = insertBukuUiEvent)
    }

    fun updateBuku() {
        viewModelScope.launch {
            try {
                bukuRepository.updateBuku(_idBuku, uiState.insertBukuUiEvent.toBuku())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
