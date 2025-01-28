package com.example.uaspam.ui.view.Kategori

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam.customwidget.CustomTopAppBar
import com.example.uaspam.model.Kategori
import com.example.uaspam.ui.PenyediaViewModel
import com.example.uaspam.ui.viewmodel.Kategori.DetailKategoriUiState
import com.example.uaspam.ui.viewmodel.Kategori.DetailKategoriViewModel

object DestinasiDetailKategori {
    const val route = "detail_kategori"
    const val titleRes = "Detail Kategori"
    const val ID_KATEGORI = "id_kategori"
    val routesWithArg = "$route/{$ID_KATEGORI}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailKategoriScreen(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailKategoriViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetailKategori.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getKategoriById()
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
                    contentDescription = "Edit Kategori"
                )
            }
        }
    ) { innerPadding ->
        DetailStatusKategori(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.kategoriDetailState,
            retryAction = { viewModel.getKategoriById() }
        )
    }
}

@Composable
fun DetailStatusKategori(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: DetailKategoriUiState
) {
    when (detailUiState) {
        is DetailKategoriUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is DetailKategoriUiState.Success -> {
            if (detailUiState.kategori.nama_kategori.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailKategori(
                    kategori = detailUiState.kategori,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }

        is DetailKategoriUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailKategori(
    modifier: Modifier = Modifier,
    kategori: Kategori
) {
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailKategori(judul = "ID Kategori", isinya = kategori.id_kategori.toString())
            ComponentDetailKategori(judul = "Nama Kategori", isinya = kategori.nama_kategori)
            ComponentDetailKategori(judul = "Deskripsi", isinya = kategori.deskripsi_kategori)
        }
    }
}

@Composable
fun ComponentDetailKategori(
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
