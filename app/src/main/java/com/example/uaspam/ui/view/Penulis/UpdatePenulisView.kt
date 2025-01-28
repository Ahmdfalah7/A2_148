package com.example.uaspam.ui.view.Penulis

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaspam.customwidget.CustomTopAppBar
import com.example.uaspam.ui.PenyediaViewModel
import com.example.uaspam.ui.viewmodel.Penulis.UpdatePenulisViewModel
import kotlinx.coroutines.launch

object DestinasiUpdatePenulis {
    const val route = "update_penulis"
    const val titleRes = "Update Penulis"
    const val ID_PENULIS = "id_penulis"
    val routesWithArg = "$route/{$ID_PENULIS}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePenulisScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: UpdatePenulisViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiUpdatePenulis.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ) { padding ->
        InsertPenulisBody(
            modifier = Modifier.padding(padding),
            insertUiState = viewModel.uiState,
            onPenulisValueChange = viewModel::UpdateInsertPenulisState,
            onSaveClick = {
                coroutineScope.launch {
                    // Memastikan update penulis dilakukan sebelum navigasi
                    viewModel.updatePenulis()
                    onNavigate()  // Navigasi setelah update selesai
                }
            }
        )
    }
}
