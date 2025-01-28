package com.example.uaspam.ui.view.Kategori

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam.customwidget.CustomTopAppBar
import com.example.uaspam.ui.PenyediaViewModel
import com.example.uaspam.ui.navigasi.DestinasiNavigasi
import com.example.uaspam.ui.viewmodel.Kategori.InsertKategoriUiEvent
import com.example.uaspam.ui.viewmodel.Kategori.InsertKategoriUiState
import com.example.uaspam.ui.viewmodel.Kategori.InsertKategoriViewModel
import kotlinx.coroutines.launch

object DestinasiInsertKategori : DestinasiNavigasi {
    override val route = "insert_kategori"
    override val titleRes = "Insert Kategori"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertKategoriScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertKategoriViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiInsertKategori.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        InsertKategoriBody(
            insertUiState = viewModel.uiState,
            onKategoriValueChange = viewModel::updateInsertKategoriState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertKategori()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun InsertKategoriBody(
    insertUiState: InsertKategoriUiState,
    onKategoriValueChange: (InsertKategoriUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputKategori(
            insertUiEvent = insertUiState.insertKategoriUiEvent,
            onValueChange = onKategoriValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@Composable
fun FormInputKategori(
    insertUiEvent: InsertKategoriUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertKategoriUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.nama_kategori,
            onValueChange = { onValueChange(insertUiEvent.copy(nama_kategori = it)) },
            label = { Text("Nama Kategori") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.deskripsi_kategori,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsi_kategori = it)) },
            label = { Text("Deskripsi Kategori") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}
