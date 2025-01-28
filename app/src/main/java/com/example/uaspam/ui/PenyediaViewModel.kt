package com.example.uaspam.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.uaspam.BukuApplications
import com.example.uaspam.ui.viewmodel.Buku.HomeBukuViewModel
import com.example.uaspam.ui.viewmodel.Buku.InsertBukuViewModel
import com.example.uaspam.ui.viewmodel.Kategori.DetailKategoriViewModel
import com.example.uaspam.ui.viewmodel.Kategori.HomeKategoriViewModel
import com.example.uaspam.ui.viewmodel.Kategori.InsertKategoriViewModel
import com.example.uaspam.ui.viewmodel.Kategori.UpdateKategoriViewModel
import com.example.uaspam.ui.viewmodel.Penerbit.DetailPenerbitViewModel
import com.example.uaspam.ui.viewmodel.Penerbit.HomePenerbitViewModel
import com.example.uaspam.ui.viewmodel.Penerbit.InsertPenerbitViewModel
import com.example.uaspam.ui.viewmodel.Penerbit.UpdatePenerbitViewModel
import com.example.uaspam.ui.viewmodel.Penulis.DetailPenulisViewModel
import com.example.uaspam.ui.viewmodel.Penulis.HomePenulisViewModel
import com.example.uaspam.ui.viewmodel.Penulis.InsertPenulisViewModel
import com.example.uaspam.ui.viewmodel.Penulis.UpdatePenulisViewModel
import com.example.uaspam.ui.viewmodel.Buku.DetailBukuViewModel
import com.example.uaspam.ui.viewmodel.Buku.UpdateBukuViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeBukuViewModel(BukuApplications().container.bukuRepository)
        }
        initializer {
            InsertBukuViewModel(
                bukuRepository = BukuApplications().container.bukuRepository,
                kategoriRepository = BukuApplications().container.kategoriRepository,
                penerbitRepository = BukuApplications().container.penerbitRepository,
                penulisRepository = BukuApplications().container.penulisRepository
            )

        }
        initializer {
            DetailBukuViewModel(
                createSavedStateHandle(),
                BukuApplications().container.bukuRepository,
                BukuApplications().container.kategoriRepository,
                BukuApplications().container.penerbitRepository,
                BukuApplications().container.penulisRepository,
            )
        }
        initializer {
            UpdateBukuViewModel(
                createSavedStateHandle(),
                BukuApplications().container.bukuRepository
            )
        }
        initializer {
            HomeKategoriViewModel(BukuApplications().container.kategoriRepository)
        }
        initializer {
            InsertKategoriViewModel(BukuApplications().container.kategoriRepository)
        }
        initializer {
            DetailKategoriViewModel(
                createSavedStateHandle(),
                BukuApplications().container.kategoriRepository
            )
        }
        initializer {
            UpdateKategoriViewModel(
                createSavedStateHandle(),
                BukuApplications().container.kategoriRepository
            )
        }
        initializer {
            HomePenerbitViewModel(BukuApplications().container.penerbitRepository)
        }
        initializer {
            InsertPenerbitViewModel(BukuApplications().container.penerbitRepository)
        }
        initializer {
            DetailPenerbitViewModel(
                createSavedStateHandle(),
                BukuApplications().container.penerbitRepository
            )
        }
        initializer {
            UpdatePenerbitViewModel(
                createSavedStateHandle(),
                BukuApplications().container.penerbitRepository
            )
        }
        initializer {
            HomePenulisViewModel(BukuApplications().container.penulisRepository)
        }
        initializer {
            InsertPenulisViewModel(BukuApplications().container.penulisRepository)
        }
        initializer {
            DetailPenulisViewModel(
                createSavedStateHandle(),
                BukuApplications().container.penulisRepository
            )
        }
        initializer {
            UpdatePenulisViewModel(
                createSavedStateHandle(),
                BukuApplications().container.penulisRepository
            )
        }
    }
}

fun CreationExtras.BukuApplications() : BukuApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BukuApplications)