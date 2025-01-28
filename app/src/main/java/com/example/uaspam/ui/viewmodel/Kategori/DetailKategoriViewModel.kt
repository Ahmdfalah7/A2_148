package com.example.uaspam.ui.viewmodel.Kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Kategori
import com.example.uaspam.repository.KategoriRepository
import com.example.uaspam.ui.view.Kategori.DestinasiDetailKategori
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailKategoriUiState {
    data class Success(val kategori: Kategori) : DetailKategoriUiState()
    object Error : DetailKategoriUiState()
    object Loading : DetailKategoriUiState()
}

class DetailKategoriViewModel(
    savedStateHandle: SavedStateHandle,
    private val kategoriRepository: KategoriRepository
) : ViewModel() {

    var kategoriDetailState: DetailKategoriUiState by mutableStateOf(DetailKategoriUiState.Loading)
        private set

    private val _idKategori: Int = savedStateHandle[DestinasiDetailKategori.ID_KATEGORI]
        ?: throw IllegalArgumentException("ID_KATEGORI harus disediakan dan berupa angka yang valid")

    init {
        getKategoriById()
    }

    fun getKategoriById() {
        viewModelScope.launch {
            kategoriDetailState = DetailKategoriUiState.Loading
            try {
                val kategori = kategoriRepository.getKategoriById(_idKategori)
                kategoriDetailState = DetailKategoriUiState.Success(kategori)
            } catch (e: IOException) {
                kategoriDetailState = DetailKategoriUiState.Error
                println("IOException: ${e.message}")
            } catch (e: HttpException) {
                kategoriDetailState = DetailKategoriUiState.Error
                println("HttpException: ${e.message}")
            } catch (e: Exception) {
                kategoriDetailState = DetailKategoriUiState.Error
                println("Unexpected error: ${e.message}")
            }
        }
    }
}
