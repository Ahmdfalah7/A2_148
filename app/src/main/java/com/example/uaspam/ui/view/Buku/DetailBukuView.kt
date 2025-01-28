package com.example.uaspam.ui.view.Buku

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam.customwidget.CustomTopAppBar
import com.example.uaspam.model.Buku
import com.example.uaspam.model.Kategori
import com.example.uaspam.model.Penerbit
import com.example.uaspam.model.Penulis
import com.example.uaspam.ui.PenyediaViewModel
import com.example.uaspam.ui.viewmodel.Buku.DetailBukuUiState
import com.example.uaspam.ui.viewmodel.Buku.DetailBukuViewModel

object DestinasiDetail {
    const val route = "detail"
    const val titleRes = "Detail Buku"
    const val ID_BUKU = "id_buku"
    val routesWithArg = "$route/{$ID_BUKU}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailBukuScreen(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailBukuViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetail.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getBukuById()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemUpdate,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Buku"
                )
            }
        }
    ) { innerPadding ->
        DetailStatus(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.bukuDetailState,
            retryAction = { viewModel.getBukuById() }
        )
    }
}

@Composable
fun DetailStatus(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: DetailBukuUiState
) {
    when (detailUiState) {
        is DetailBukuUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is DetailBukuUiState.Success -> {
            if (detailUiState.buku.nama_buku.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailBuku(
                    buku = detailUiState.buku,
                    kategori = detailUiState.kategori,
                    penulis = detailUiState.penulis,
                    penerbit = detailUiState.penerbit,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }

        is DetailBukuUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailBuku(
    modifier: Modifier = Modifier,
    buku: Buku,
    kategori: Kategori?,
    penulis: Penulis?,
    penerbit: Penerbit?
) {
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailBuku(judul = "ID Buku", isinya = buku.id_buku.toString())
            ComponentDetailBuku(judul = "Nama Buku", isinya = buku.nama_buku)
            ComponentDetailBuku(judul = "Deskripsi", isinya = buku.deskripsi_buku)
            ComponentDetailBuku(judul = "Tanggal Terbit", isinya = buku.tanggal_terbit)
            ComponentDetailBuku(judul = "Status", isinya = buku.status_buku)
            ComponentDetailBuku(judul = "Kategori", isinya = (kategori?.id_kategori?: "Tidak ada kategori").toString())
            ComponentDetailBuku(judul = "Penulis", isinya = (penulis?.id_penulis ?: "Tidak ada penulis").toString())
            ComponentDetailBuku(judul = "Penerbit", isinya = (penerbit?.id_penerbit ?: "Tidak ada penerbit").toString())
        }
    }
}

@Composable
fun ComponentDetailBuku(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
