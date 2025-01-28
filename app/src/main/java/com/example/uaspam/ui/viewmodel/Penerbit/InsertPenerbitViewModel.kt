package com.example.uaspam.ui.viewmodel.Penerbit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Penerbit
import com.example.uaspam.repository.PenerbitRepository
import kotlinx.coroutines.launch

class InsertPenerbitViewModel(private val penerbitRepository: PenerbitRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertPenerbitUiState())
        private set

    fun updateInsertPenerbitState(insertPenerbitUiEvent: InsertPenerbitUiEvent) {
        uiState = InsertPenerbitUiState(insertPenerbitUiEvent = insertPenerbitUiEvent)
    }

    fun insertPenerbit() {
        viewModelScope.launch {
            try {
                penerbitRepository.insertPenerbit(uiState.insertPenerbitUiEvent.toPenerbit())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertPenerbitUiState(
    val insertPenerbitUiEvent: InsertPenerbitUiEvent = InsertPenerbitUiEvent()
)

data class InsertPenerbitUiEvent(
    val id_penerbit: Int = 0,
    val nama_penerbit: String = "",
    val alamat_penerbit: String = "",
    val telepon_penerbit: String = ""
)

fun InsertPenerbitUiEvent.toPenerbit(): Penerbit = Penerbit(
    id_penerbit = id_penerbit,
    nama_penerbit = nama_penerbit,
    alamat_penerbit = alamat_penerbit,
    telepon_penerbit = telepon_penerbit
)

fun Penerbit.toInsertPenerbitUiEvent(): InsertPenerbitUiEvent = InsertPenerbitUiEvent(
    id_penerbit = id_penerbit,
    nama_penerbit = nama_penerbit,
    alamat_penerbit = alamat_penerbit,
    telepon_penerbit = telepon_penerbit
)
