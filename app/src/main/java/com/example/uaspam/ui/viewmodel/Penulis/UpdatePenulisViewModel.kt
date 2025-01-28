package com.example.uaspam.ui.viewmodel.Penulis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Penulis
import com.example.uaspam.repository.PenulisRepository
import kotlinx.coroutines.launch

class UpdatePenulisViewModel(
    savedStateHandle: SavedStateHandle,
    private val penulisRepository: PenulisRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertPenulisUiState()) // Menggunakan InsertPenulisUiState
        private set

    private val _idPenulis: Int = checkNotNull(savedStateHandle["id_penulis"])

    init {
        viewModelScope.launch {
            val penulis = penulisRepository.getPenulisById(_idPenulis)
            // Mengonversi Penulis menjadi InsertPenulisUiEvent untuk state UI
            uiState = InsertPenulisUiState(insertPenulisUiEvent = penulis.toInsertPenulisUiEvent())
        }
    }

    // Fungsi untuk memperbarui nilai state InsertPenulisUiEvent
    fun UpdateInsertPenulisState(insertPenulisUiEvent: InsertPenulisUiEvent) {
        uiState = InsertPenulisUiState(insertPenulisUiEvent = insertPenulisUiEvent)
    }

    // Fungsi untuk memperbarui data penulis
    suspend fun updatePenulis() {
        viewModelScope.launch {
            try {
                penulisRepository.updatePenulis(_idPenulis, uiState.insertPenulisUiEvent.toPenulis())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
