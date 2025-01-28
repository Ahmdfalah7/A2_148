package com.example.uaspam.ui.viewmodel.Kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uaspam.model.Kategori
import com.example.uaspam.repository.KategoriRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeKategoriUiState {
    data class Success(val kategori: List<Kategori>) : HomeKategoriUiState()
    object Error : HomeKategoriUiState()
    object Loading : HomeKategoriUiState()
}

class HomeKategoriViewModel(private val kategoriRepository: KategoriRepository) : ViewModel() {
    var kategoriUiState: HomeKategoriUiState by mutableStateOf(HomeKategoriUiState.Loading)
        private set

    init {
        getKategori()
    }

    fun getKategori() {
        viewModelScope.launch {
            kategoriUiState = HomeKategoriUiState.Loading
            kategoriUiState = try {
                HomeKategoriUiState.Success(kategoriRepository.getKategori().data)
            } catch (e: IOException) {
                HomeKategoriUiState.Error
            } catch (e: HttpException) {
                HomeKategoriUiState.Error
            }
        }
    }

    fun deleteKategori(id: Int) {
        viewModelScope.launch {
            try {
                kategoriRepository.deleteKategori(id)
                // Refresh data after deletion
            } catch (e: IOException) {
                kategoriUiState = HomeKategoriUiState.Error
            } catch (e: HttpException) {
                kategoriUiState = HomeKategoriUiState.Error
            }
        }
    }
}
