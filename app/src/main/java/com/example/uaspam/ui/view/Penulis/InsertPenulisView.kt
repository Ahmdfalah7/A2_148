package com.example.uaspam.ui.view.Penulis

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
import com.example.uaspam.ui.viewmodel.Penulis.InsertPenulisUiEvent
import com.example.uaspam.ui.viewmodel.Penulis.InsertPenulisUiState
import com.example.uaspam.ui.viewmodel.Penulis.InsertPenulisViewModel
import kotlinx.coroutines.launch

object DestinasiInsertPenulis : DestinasiNavigasi {
    override val route = "insert_penulis"
    override val titleRes = "Insert Penulis"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertPenulisScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPenulisViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiInsertPenulis.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        InsertPenulisBody(
            insertUiState = viewModel.uiState,
            onPenulisValueChange = viewModel::updateInsertPenulisState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPenulis()
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
fun InsertPenulisBody(
    insertUiState: InsertPenulisUiState,
    onPenulisValueChange: (InsertPenulisUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputPenulis(
            insertUiEvent = insertUiState.insertPenulisUiEvent,
            onValueChange = onPenulisValueChange,
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
fun FormInputPenulis(
    insertUiEvent: InsertPenulisUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPenulisUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.nama_penulis,
            onValueChange = { onValueChange(insertUiEvent.copy(nama_penulis = it)) },
            label = { Text("Nama Penulis") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.biografi,
            onValueChange = { onValueChange(insertUiEvent.copy(biografi = it)) },
            label = { Text("Biografi Penulis") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.kontak,
            onValueChange = { onValueChange(insertUiEvent.copy(kontak = it)) },
            label = { Text("Kontak Penulis") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}
