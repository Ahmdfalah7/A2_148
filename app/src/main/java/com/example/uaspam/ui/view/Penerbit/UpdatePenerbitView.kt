package com.example.uaspam.ui.view.Penerbit

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
import com.example.uaspam.ui.viewmodel.Penerbit.UpdatePenerbitViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdatePenerbit {
    const val route = "update_penerbit"
    const val titleRes = "Update Penerbit"
    const val ID_PENERBIT = "id_penerbit"
    val routesWithArg = "$route/{$ID_PENERBIT}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePenerbitScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: UpdatePenerbitViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiUpdatePenerbit.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ) { padding ->
        InsertPenerbitBody(
            modifier = Modifier.padding(padding),
            insertUiState = viewModel.uiState,
            onPenerbitValueChange = viewModel::updateInsertPenerbitState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePenerbit()
                    delay(600)
                    withContext(Dispatchers.Main) {
                        onNavigate()
                    }
                }
            }
        )
    }
}
