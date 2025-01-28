package com.example.uaspam.ui.view.Buku

import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam.customwidget.CustomTopAppBar
import com.example.uaspam.ui.PenyediaViewModel
import com.example.uaspam.ui.navigasi.DestinasiNavigasi
import com.example.uaspam.ui.viewmodel.Buku.InsertBukuUiEvent
import com.example.uaspam.ui.viewmodel.Buku.InsertBukuUiState
import com.example.uaspam.ui.viewmodel.Buku.InsertBukuViewModel
import kotlinx.coroutines.launch

object DestinasiInsertBuku : DestinasiNavigasi {
    override val route = "insert_buku"
    override val titleRes = "Insert Buku"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertBukuScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertBukuViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiInsertBuku.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        InsertBukuBody(
            insertUiState = viewModel.uiState,
            onBukuValueChange = viewModel::updateInsertBukuState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertBuku()
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
fun InsertBukuBody(
    insertUiState: InsertBukuUiState,
    onBukuValueChange: (InsertBukuUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputBuku(
            insertUiEvent = insertUiState.insertBukuUiEvent,
            onValueChange = onBukuValueChange,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputBuku(
    insertUiEvent: InsertBukuUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertBukuUiEvent) -> Unit = {},
    viewModel: InsertBukuViewModel = viewModel(factory = PenyediaViewModel.Factory),
    enabled: Boolean = true
) {
    val kategoriOptions = viewModel.uiState.kategoriOptions
    val penerbitOptions = viewModel.uiState.penerbitOptions
    val penulisOptions = viewModel.uiState.penulisOptions
    val context = LocalContext.current

    // State to store the selected date
    var selectedDate by remember { mutableStateOf(insertUiEvent.tanggal_terbit) }
    // State for radio button selection
    var selectedStatus by remember { mutableStateOf(insertUiEvent.status_buku) }

    val calendar = Calendar.getInstance()

    // Using DatePickerDialog from the Android SDK
    val datePickerDialog = remember {
        android.app.DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                // Update the selected date when the user picks a date
                val newDate = "$dayOfMonth-${month + 1}-$year"
                selectedDate = newDate
                onValueChange(insertUiEvent.copy(tanggal_terbit = newDate))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.nama_buku,
            onValueChange = { onValueChange(insertUiEvent.copy(nama_buku = it)) },
            label = { Text("Nama Buku") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.deskripsi_buku,
            onValueChange = { onValueChange(insertUiEvent.copy(deskripsi_buku = it)) },
            label = { Text("Deskripsi Buku") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = selectedDate,
            onValueChange = {},
            label = { Text("Tanggal Terbit") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { datePickerDialog.show() }) {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                }
            }
        )

        // Menambahkan radio button untuk status buku
        Text(text = "Status Buku", style = MaterialTheme.typography.bodyLarge)
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            listOf("Tersedia", "Dipesan", "Habis").forEach { status ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedStatus == status,
                        onClick = {
                            selectedStatus = status
                            onValueChange(insertUiEvent.copy(status_buku = status))
                        }
                    )
                    Text(text = status)
                }
            }
        }

        DropDownAll(
            title = "Pilih Kategori",
            options = kategoriOptions.map { it.label },
            selectedOption = kategoriOptions.find { it.id_kategori == insertUiEvent.id_kategori }?.label.orEmpty(),
            onOptionSelected = { label ->
                val selected = kategoriOptions.find { it.label == label }
                onValueChange(insertUiEvent.copy(id_kategori = selected?.id_kategori ?: 0))
            }
        )

        DropDownAll(
            title = "Pilih Penerbit",
            options = penerbitOptions.map { it.label },
            selectedOption = penerbitOptions.find { it.id_penerbit == insertUiEvent.id_penerbit }?.label.orEmpty(),
            onOptionSelected = { label ->
                val selected = penerbitOptions.find { it.label == label }
                onValueChange(insertUiEvent.copy(id_penerbit = selected?.id_penerbit ?: 0))
            }
        )

        DropDownAll(
            title = "Pilih Penulis",
            options = penulisOptions.map { it.label },
            selectedOption = penulisOptions.find { it.id_penulis == insertUiEvent.id_penulis }?.label.orEmpty(),
            onOptionSelected = { label ->
                val selected = penulisOptions.find { it.label == label }
                onValueChange(insertUiEvent.copy(id_penulis = selected?.id_penulis ?: 0))
            }
        )
    }
}
@Composable
fun DropDownAll(
    title: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = { },
            readOnly = true,
            label = { Text(text = title) },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
