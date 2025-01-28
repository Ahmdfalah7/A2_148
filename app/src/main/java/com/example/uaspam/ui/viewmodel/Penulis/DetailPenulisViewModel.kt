package com.example.uaspam.ui.viewmodel.Penulis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Penulis
import com.example.uaspam.repository.PenulisRepository
import com.example.uaspam.ui.view.Kategori.DestinasiDetailKategori
import com.example.uaspam.ui.view.Penulis.DestinasiDetailPenulis
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailPenulisUiState {
    data class Success(val penulis: Penulis) : DetailPenulisUiState()
    object Error : DetailPenulisUiState()
    object Loading : DetailPenulisUiState()
}

class DetailPenulisViewModel(
    savedStateHandle: SavedStateHandle,
    private val penulisRepository: PenulisRepository
) : ViewModel() {

    var penulisDetailState: DetailPenulisUiState by mutableStateOf(DetailPenulisUiState.Loading)
        private set

    private val _idPenulis: Int = savedStateHandle[DestinasiDetailPenulis.ID_PENULIS]
        ?: throw IllegalArgumentException("ID_PENULIS harus disediakan dan berupa angka yang valid")

    init {
        getPenulisById()
    }

    fun getPenulisById() {
        viewModelScope.launch {
            penulisDetailState = DetailPenulisUiState.Loading
            try {
                val penulis = penulisRepository.getPenulisById(_idPenulis)
                penulisDetailState = DetailPenulisUiState.Success(penulis)
            } catch (e: IOException) {
                penulisDetailState = DetailPenulisUiState.Error
            } catch (e: HttpException) {
                penulisDetailState = DetailPenulisUiState.Error
            }
        }
    }
}
