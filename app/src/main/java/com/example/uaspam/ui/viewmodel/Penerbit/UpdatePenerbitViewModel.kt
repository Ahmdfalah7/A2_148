package com.example.uaspam.ui.viewmodel.Penerbit


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.repository.PenerbitRepository
import kotlinx.coroutines.launch

class UpdatePenerbitViewModel(
    savedStateHandle: SavedStateHandle,
    private val penerbitRepository: PenerbitRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertPenerbitUiState())
        private set

    private val _idPenerbit: Int = checkNotNull(savedStateHandle["id_penerbit"])

    init {
        viewModelScope.launch {
            val penerbit = penerbitRepository.getPenerbitById(_idPenerbit)
            uiState = InsertPenerbitUiState(insertPenerbitUiEvent = penerbit.toInsertPenerbitUiEvent())
        }
    }

    fun updateInsertPenerbitState(insertPenerbitUiEvent: InsertPenerbitUiEvent) {
        uiState = InsertPenerbitUiState(insertPenerbitUiEvent = insertPenerbitUiEvent)
    }

    suspend fun updatePenerbit() {
        viewModelScope.launch {
            try {
                penerbitRepository.updatePenerbit(_idPenerbit, uiState.insertPenerbitUiEvent.toPenerbit())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
