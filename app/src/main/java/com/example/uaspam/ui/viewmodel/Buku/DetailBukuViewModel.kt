package com.example.uaspam.ui.viewmodel.Buku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uaspam.model.Buku
import com.example.uaspam.model.Kategori
import com.example.uaspam.model.Penerbit
import com.example.uaspam.model.Penulis
import com.example.uaspam.repository.BukuRepository
import com.example.uaspam.repository.KategoriRepository
import com.example.uaspam.repository.PenerbitRepository
import com.example.uaspam.repository.PenulisRepository
import com.example.uaspam.ui.view.Buku.DestinasiDetail
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// State untuk UI Detail Buku
sealed class DetailBukuUiState {
    data class Success(
        val buku: Buku,
        val kategori: Kategori?,
        val penulis: Penulis?,
        val penerbit: Penerbit?
    ) : DetailBukuUiState()
    object Error : DetailBukuUiState()
    object Loading : DetailBukuUiState()
}

class DetailBukuViewModel(
    savedStateHandle: SavedStateHandle,
    private val bukuRepository: BukuRepository,
    private val kategoriRepository: KategoriRepository,
    private val penerbitRepository: PenerbitRepository,
    private val penulisRepository: PenulisRepository,
) : ViewModel() {

    var bukuDetailState: DetailBukuUiState by mutableStateOf(DetailBukuUiState.Loading)
        private set

    private val _idBuku: Int = savedStateHandle[DestinasiDetail.ID_BUKU]
        ?: throw IllegalArgumentException("ID_BUKU harus disediakan dan berupa angka yang valid")

    init {
        getBukuById()
    }

    // Fungsi untuk mengambil buku berdasarkan ID
    fun getBukuById() {
        viewModelScope.launch {
            bukuDetailState = DetailBukuUiState.Loading
            bukuDetailState = try {
                val buku = bukuRepository.getBukuById(_idBuku)
                // Setelah buku ditemukan, ambil kategori, penulis, dan penerbit
                val kategori = kategoriRepository.getKategoriById(buku.id_kategori)
                val penulis = penulisRepository.getPenulisById(buku.id_penulis)
                val penerbit = penerbitRepository.getPenerbitById(buku.id_penerbit)

                // Mengembalikan state dengan buku dan informasi terkait
                DetailBukuUiState.Success(buku, kategori, penulis, penerbit)
            } catch (e: IOException) {
                DetailBukuUiState.Error
            } catch (e: HttpException) {
                DetailBukuUiState.Error
            }
        }
    }
}
