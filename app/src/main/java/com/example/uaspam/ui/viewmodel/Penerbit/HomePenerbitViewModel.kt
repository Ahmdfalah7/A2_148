package com.example.uaspam.ui.viewmodel.Penerbit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.uaspam.model.Penerbit
import com.example.uaspam.repository.PenerbitRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomePenerbitUiState {
    data class Success(val penerbit: List<Penerbit>) : HomePenerbitUiState()
    object Error : HomePenerbitUiState()
    object Loading : HomePenerbitUiState()
}

class HomePenerbitViewModel(private val penerbitRepository: PenerbitRepository) : ViewModel() {
    var penerbitUiState: HomePenerbitUiState by mutableStateOf(HomePenerbitUiState.Loading)
        private set

    init {
        getPenerbit()
    }

    fun getPenerbit() {
        viewModelScope.launch {
            penerbitUiState = HomePenerbitUiState.Loading
            penerbitUiState = try {
                HomePenerbitUiState.Success(penerbitRepository.getPenerbit().data)
            } catch (e: IOException) {
                HomePenerbitUiState.Error
            } catch (e: HttpException) {
                HomePenerbitUiState.Error
            }
        }
    }

    fun deletePenerbit(id: Int) {
        viewModelScope.launch {
            try {
                penerbitRepository.deletePenerbit(id)
                // Refresh data after deletion
                getPenerbit()
            } catch (e: IOException) {
                penerbitUiState = HomePenerbitUiState.Error
            } catch (e: HttpException) {
                penerbitUiState = HomePenerbitUiState.Error
            }
        }
    }
}
