package com.example.uaspam.ui.view.Penerbit

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
import com.example.uaspam.model.Penerbit
import com.example.uaspam.ui.PenyediaViewModel
import com.example.uaspam.ui.viewmodel.Penerbit.DetailPenerbitUiState
import com.example.uaspam.ui.viewmodel.Penerbit.DetailPenerbitViewModel

object DestinasiDetailPenerbit {
    const val route = "detail_penerbit"
    const val titleRes = "Detail Penerbit"
    const val ID_PENERBIT = "id_penerbit"
    val routesWithArg = "$route/{$ID_PENERBIT}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPenerbitScreen(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailPenerbitViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetailPenerbit.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getPenerbitById()
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
                    contentDescription = "Edit Penerbit"
                )
            }
        }
    ) { innerPadding ->
        DetailStatusPenerbit(
            modifier = Modifier.padding(innerPadding),
            detailUiState = viewModel.penerbitDetailState,
            retryAction = { viewModel.getPenerbitById() }
        )
    }
}

@Composable
fun DetailStatusPenerbit(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailUiState: DetailPenerbitUiState
) {
    when (detailUiState) {
        is DetailPenerbitUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is DetailPenerbitUiState.Success -> {
            if (detailUiState.penerbit.nama_penerbit.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailPenerbit(
                    penerbit = detailUiState.penerbit,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }

        is DetailPenerbitUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailPenerbit(
    modifier: Modifier = Modifier,
    penerbit: Penerbit
) {
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailPenerbit(judul = "ID PENERBIT", isinya = penerbit.id_penerbit.toString())
            ComponentDetailPenerbit(judul = "Nama Penerbit", isinya = penerbit.nama_penerbit)
            ComponentDetailPenerbit(judul = "Alamat", isinya = penerbit.alamat_penerbit)
            ComponentDetailPenerbit(judul = "Telepon", isinya = penerbit.telepon_penerbit)
        }
    }
}

@Composable
fun ComponentDetailPenerbit(
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
