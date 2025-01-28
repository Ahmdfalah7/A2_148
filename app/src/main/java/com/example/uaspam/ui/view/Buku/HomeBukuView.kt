package com.example.uaspam.ui.view.Buku

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.uaspam.customwidget.CustomTopAppBar
import com.example.uaspam.customwidget.MenuButton
import com.example.uaspam.model.Buku
import com.example.uaspam.ui.PenyediaViewModel
import com.example.uaspam.ui.navigasi.DestinasiNavigasi
import com.example.uaspam.ui.viewmodel.Buku.HomeBukuUiState
import com.example.uaspam.ui.viewmodel.Buku.HomeBukuViewModel

object DestinasiBukuHome : DestinasiNavigasi {
    override val route = "buku_home"
    override val titleRes = "Home Buku"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BukuHomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToBuku: () -> Unit,
    navigateToKategori: () -> Unit,
    navigateToPenerbit: () -> Unit,
    navigateToPenulis: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeBukuViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiBukuHome.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getBuku()
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
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Buku")
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
            BukuHomeStatus(
                bukuUiState = viewModel.bukuUiState,
                retryAction = { viewModel.getBuku() },
                modifier = Modifier.padding(16.dp),
                onDetailClick = onDetailClick,
                onDeleteClick = {
                    viewModel.deleteBuku(it.id_buku)
                    viewModel.getBuku()
                }
            )
        }
    }
}
@Composable
fun BukuHomeStatus(
    bukuUiState: HomeBukuUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Buku) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (bukuUiState) {
        is HomeBukuUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeBukuUiState.Success -> {
            if (bukuUiState.buku.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Tidak Ada Data Buku",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            } else {
                BukuLayout(
                    buku = bukuUiState.buku,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_buku.toString()) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is HomeBukuUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun BukuLayout(
    buku: List<Buku>,
    modifier: Modifier = Modifier,
    onDetailClick: (Buku) -> Unit,
    onDeleteClick: (Buku) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(buku) { buku ->
            BukuCard(
                buku = buku,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(buku) },
                onDeleteClick = { onDeleteClick(buku) }
            )
        }
    }
}

@Composable
fun BukuCard(
    buku: Buku,
    modifier: Modifier = Modifier,
    onDeleteClick: (Buku) -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                showDialog = false
                onDeleteClick(buku)
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
                    text = buku.nama_buku,
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
                text = buku.deskripsi_buku,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Text(
                text = "Terbit: ${buku.tanggal_terbit}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray
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
        title = { Text("Hapus Buku") },
        text = { Text("Apakah Anda yakin ingin menghapus buku ini?") },
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
