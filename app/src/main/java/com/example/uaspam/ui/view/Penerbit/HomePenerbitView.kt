package com.example.uaspam.ui.view.Penerbit

import com.example.uaspam.ui.navigasi.DestinasiNavigasi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam.R
import com.example.uaspam.model.Penerbit
import com.example.uaspam.ui.viewmodel.Penerbit.HomePenerbitUiState
import com.example.uaspam.ui.viewmodel.Penerbit.HomePenerbitViewModel
import com.example.uaspam.customwidget.CustomTopAppBar
import com.example.uaspam.customwidget.MenuButton
import com.example.uaspam.ui.PenyediaViewModel

object DestinasiPenerbitHome : DestinasiNavigasi {
    override val route = "penerbit_home"
    override val titleRes = "Home Penerbit"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PenerbitHomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToBuku: () -> Unit,
    navigateToKategori: () -> Unit,
    navigateToPenerbit: () -> Unit,
    navigateToPenulis: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomePenerbitViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiPenerbitHome.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getPenerbit()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Penerbit")
            }
        },
        bottomBar = {
            MenuButton(
                onBukuClick = navigateToBuku,
                onKategoriClick = navigateToKategori,
                onPenerbitClick = navigateToPenerbit,
                onPenulisClick = navigateToPenulis,
                modifier = Modifier
                    .background(Color(0xFF001F3F))
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF6A1B9A), Color(0xFF3949AB))
                    )
                )
                .padding(innerPadding)
        ) {
            PenerbitHomeStatus(
                penerbitUiState = viewModel.penerbitUiState,
                retryAction = { viewModel.getPenerbit() },
                modifier = Modifier.padding(16.dp),
                onDetailClick = onDetailClick,
                onDeleteClick = {
                    viewModel.deletePenerbit(it.id_penerbit)
                    viewModel.getPenerbit()
                }
            )
        }
    }
}

@Composable
fun PenerbitHomeStatus(
    penerbitUiState: HomePenerbitUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Penerbit) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (penerbitUiState) {
        is HomePenerbitUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomePenerbitUiState.Success -> {
            if (penerbitUiState.penerbit.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Tidak Ada Data Penerbit",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            } else {
                PenerbitLayout(
                    penerbit = penerbitUiState.penerbit,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_penerbit.toString()) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is HomePenerbitUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun PenerbitLayout(
    penerbit: List<Penerbit>,
    modifier: Modifier = Modifier,
    onDetailClick: (Penerbit) -> Unit,
    onDeleteClick: (Penerbit) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(penerbit) { penerbit ->
            PenerbitCard(
                penerbit = penerbit,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(penerbit) },
                onDeleteClick = { onDeleteClick(penerbit) }
            )
        }
    }
}

@Composable
fun PenerbitCard(
    penerbit: Penerbit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Penerbit) -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                showDialog = false
                onDeleteClick(penerbit)
            },
            onDeleteCancel = { showDialog = false }
        )
    }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.9f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = penerbit.nama_penerbit,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF3949AB),
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { showDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
            Text(
                text = penerbit.alamat_penerbit,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Text(
                text = penerbit.telepon_penerbit,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text("Hapus Penerbit") },
        text = { Text("Apakah Anda yakin ingin menghapus penerbit ini?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Batal")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Hapus")
            }
        }
    )
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.loading1),
            contentDescription = stringResource(R.string.loading),
            modifier = Modifier.size(100.dp)
        )
    }
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.error), contentDescription = null)
        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error
        )
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}
