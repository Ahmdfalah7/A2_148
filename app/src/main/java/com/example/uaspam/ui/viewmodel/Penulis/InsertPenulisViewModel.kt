package com.example.uaspam.ui.viewmodel.Penulis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Penulis
import com.example.uaspam.repository.PenulisRepository
import kotlinx.coroutines.launch

class InsertPenulisViewModel(private val penulisRepository: PenulisRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertPenulisUiState())
        private set

    fun updateInsertPenulisState(insertPenulisUiEvent: InsertPenulisUiEvent) {
        uiState = InsertPenulisUiState(insertPenulisUiEvent = insertPenulisUiEvent)
    }

    fun insertPenulis() {
        viewModelScope.launch {
            try {
                penulisRepository.insertPenulis(uiState.insertPenulisUiEvent.toPenulis())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertPenulisUiState(
    val insertPenulisUiEvent: InsertPenulisUiEvent = InsertPenulisUiEvent()
)

data class InsertPenulisUiEvent(
    val id_penulis: Int = 0,
    val nama_penulis: String = "",
    val biografi: String = "",
    val kontak: String = ""
)

fun InsertPenulisUiEvent.toPenulis(): Penulis = Penulis(
    id_penulis = id_penulis,
    nama_penulis = nama_penulis,
    biografi = biografi,
    kontak = kontak
)

fun Penulis.toInsertPenulisUiEvent(): InsertPenulisUiEvent = InsertPenulisUiEvent(
    id_penulis = id_penulis,
    nama_penulis = nama_penulis,
    biografi = biografi,
    kontak = kontak
)
