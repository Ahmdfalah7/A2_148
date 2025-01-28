package com.example.uaspam.ui.viewmodel.Penerbit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Penerbit
import com.example.uaspam.repository.PenerbitRepository
import com.example.uaspam.ui.view.Penerbit.DestinasiDetailPenerbit
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailPenerbitUiState {
    data class Success(val penerbit: Penerbit) : DetailPenerbitUiState()
    object Error : DetailPenerbitUiState()
    object Loading : DetailPenerbitUiState()
}

class DetailPenerbitViewModel(
    savedStateHandle: SavedStateHandle,
    private val penerbitRepository: PenerbitRepository
) : ViewModel() {

    var penerbitDetailState: DetailPenerbitUiState by mutableStateOf(DetailPenerbitUiState.Loading)
        private set

    private val _idPenerbit: Int = savedStateHandle[DestinasiDetailPenerbit.ID_PENERBIT]
        ?: throw IllegalArgumentException("ID_PENERBIT harus disediakan dan berupa angka yang valid")

    init {
        getPenerbitById()
    }

    fun getPenerbitById() {
        viewModelScope.launch {
            penerbitDetailState = DetailPenerbitUiState.Loading
            try {
                val penerbit = penerbitRepository.getPenerbitById(_idPenerbit)
                penerbitDetailState = DetailPenerbitUiState.Success(penerbit)
            } catch (e: IOException) {
                // Tangani error jaringan atau masalah I/O
                penerbitDetailState = DetailPenerbitUiState.Error
            } catch (e: HttpException) {
                // Tangani error terkait HTTP (misalnya 404, 500, dll)
                penerbitDetailState = DetailPenerbitUiState.Error
            }
        }
    }
}
