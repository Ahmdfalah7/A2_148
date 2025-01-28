package com.example.uaspam.ui.viewmodel.Buku

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uaspam.model.Buku
import com.example.uaspam.repository.BukuRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeBukuUiState {
    data class Success(val buku: List<Buku>) : HomeBukuUiState()
    data class Error(val message: String) : HomeBukuUiState()
    object Loading : HomeBukuUiState()
}

class HomeBukuViewModel(private val bukuRepository: BukuRepository) : ViewModel() {
    var bukuUiState: HomeBukuUiState by mutableStateOf(HomeBukuUiState.Loading)
        private set

    init {
        getBuku()
    }

    fun getBuku() {
        viewModelScope.launch {
            Log.d("HomeBukuViewModel", "Fetching buku data...") // Log saat memulai operasi
            bukuUiState = HomeBukuUiState.Loading
            try {
                val result = bukuRepository.getBuku().data
                bukuUiState = HomeBukuUiState.Success(result)
                Log.d("HomeBukuViewModel", "Buku data fetched successfully: ${result.size} items") // Log sukses
            } catch (e: IOException) {
                bukuUiState = HomeBukuUiState.Error("Terjadi kesalahan jaringan: ${e.message}")
                Log.e("HomeBukuViewModel", "Network error: ${e.message}") // Log error jaringan
            } catch (e: HttpException) {
                bukuUiState = HomeBukuUiState.Error("Terjadi kesalahan HTTP: ${e.message}")
                Log.e("HomeBukuViewModel", "HTTP error: ${e.message}") // Log error HTTP
            } catch (e: Exception) {
                bukuUiState = HomeBukuUiState.Error("Terjadi kesalahan: ${e.message}")
                Log.e("HomeBukuViewModel", "Unexpected error: ${e.message}") // Log kesalahan lainnya
            }
        }
    }

    fun deleteBuku(id: Int) {
        viewModelScope.launch {
            Log.d("HomeBukuViewModel", "Deleting buku with id: $id...") // Log saat memulai penghapusan
            try {
                bukuRepository.deleteBuku(id)
                getBuku() // Memperbarui data setelah penghapusan
                Log.d("HomeBukuViewModel", "Buku with id: $id deleted successfully.") // Log setelah penghapusan berhasil
            } catch (e: IOException) {
                bukuUiState = HomeBukuUiState.Error("Terjadi kesalahan jaringan saat menghapus buku: ${e.message}")
                Log.e("HomeBukuViewModel", "Network error while deleting: ${e.message}") // Log error jaringan saat menghapus
            } catch (e: HttpException) {
                bukuUiState = HomeBukuUiState.Error("Terjadi kesalahan HTTP saat menghapus buku: ${e.message}")
                Log.e("HomeBukuViewModel", "HTTP error while deleting: ${e.message}") // Log error HTTP saat menghapus
            } catch (e: Exception) {
                bukuUiState = HomeBukuUiState.Error("Terjadi kesalahan saat menghapus buku: ${e.message}")
                Log.e("HomeBukuViewModel", "Unexpected error while deleting: ${e.message}") // Log kesalahan lainnya saat menghapus
            }
        }
    }
}
