package com.example.uaspam.ui.viewmodel.Penulis

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Penulis
import com.example.uaspam.repository.PenulisRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class HomePenulisUiState {
    object Loading : HomePenulisUiState()
    data class Success(val penulis: List<Penulis>) : HomePenulisUiState()
    object Error : HomePenulisUiState()
}

class HomePenulisViewModel(
    private val penulisRepository: PenulisRepository
) : ViewModel() {

    var penulisUiState: HomePenulisUiState by mutableStateOf(HomePenulisUiState.Loading)
        private set

    init {
        getPenulis()
    }

    fun getPenulis() {
        viewModelScope.launch {
            penulisUiState = HomePenulisUiState.Loading
            try {
                val response = penulisRepository.getPenulis()
                penulisUiState = HomePenulisUiState.Success(response.data)
            } catch (e: IOException) {
                penulisUiState = HomePenulisUiState.Error
            } catch (e: HttpException) {
                penulisUiState = HomePenulisUiState.Error
            }
        }
    }

    fun deletePenulis(id: Int) {
        viewModelScope.launch {
            try {
                penulisRepository.deletePenulis(id)
                getPenulis() // Refresh data setelah delete
            } catch (e: IOException) {
                penulisUiState = HomePenulisUiState.Error
            } catch (e: HttpException) {
                penulisUiState = HomePenulisUiState.Error
            }
        }
    }
}
