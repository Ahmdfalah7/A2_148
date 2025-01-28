package com.example.uaspam.ui.view.Penulis

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
import com.example.uaspam.model.Penulis
import com.example.uaspam.ui.PenyediaViewModel
import com.example.uaspam.ui.view.Kategori.ComponentDetailKategori
import com.example.uaspam.ui.viewmodel.Penulis.DetailPenulisUiState
import com.example.uaspam.ui.viewmodel.Penulis.DetailPenulisViewModel

object DestinasiDetailPenulis {
    const val route = "detail_penulis"
    const val titleRes = "Detail Penulis"
    const val ID_PENULIS = "id_penulis"
    val routesWithArg = "$route/{$ID_PENULIS}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPenulisScreen(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailPenulisViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetailPenulis.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getPenulisById()
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
                    contentDescription = "Edit Penulis"
                )
            }
        }
    ) { innerPadding ->
        DetailStatusPenulis(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.penulisDetailState,
            retryAction = { viewModel.getPenulisById() }
        )
    }
}

@Composable
fun DetailStatusPenulis(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: DetailPenulisUiState
) {
    when (detailUiState) {
        is DetailPenulisUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is DetailPenulisUiState.Success -> {
            if (detailUiState.penulis.nama_penulis.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailPenulis(
                    penulis = detailUiState.penulis,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }

        is DetailPenulisUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailPenulis(
    modifier: Modifier = Modifier,
    penulis: Penulis
) {
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailKategori(judul = "ID PENULIS", isinya = penulis.id_penulis.toString())
            ComponentDetailPenulis(judul = "Nama Penulis", isinya = penulis.nama_penulis)
            ComponentDetailPenulis(judul = "Biografi", isinya = penulis.biografi)
            ComponentDetailPenulis(judul = "Kontak", isinya = penulis.kontak)
        }
    }
}

@Composable
fun ComponentDetailPenulis(
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
