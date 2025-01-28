package com.example.uaspam.ui.view.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uaspam.R
import com.example.uaspam.customwidget.TopHomeAppBar
import com.example.uaspam.ui.navigasi.DestinasiNavigasi

object DestinasiHome : DestinasiNavigasi {
    override val route = "App_home"
    override val titleRes = "Home App"
}

@Composable
fun HomeAppView(
    onBukuClick: () -> Unit,
    onKategoriClick: () -> Unit,
    onPenerbitClick: () -> Unit,
    onPenulisClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopHomeAppBar(
                onBack = {}, // Tidak ada fungsi kembali untuk halaman Home
                showBackButton = false, // Tombol kembali disembunyikan
                modifier = Modifier.background(Color(0xFF001F3F)) // Latar belakang biru navy
            )
        },
        content = { paddingValues ->
            BodyHomeAppView(
                onBukuClick = onBukuClick,
                onKategoriClick = onKategoriClick,
                onPenerbitClick = onPenerbitClick,
                onPenulisClick = onPenulisClick,
                paddingValues = paddingValues,
            )
        }
    )
}

@Composable
fun BodyHomeAppView(
    onBukuClick: () -> Unit = { },
    onKategoriClick: () -> Unit = { },
    onPenerbitClick: () -> Unit = { },
    onPenulisClick: () -> Unit = { },
    paddingValues: PaddingValues,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF001F3F)) // Latar belakang biru navy
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Daftar kartu dengan gambar berbeda
        listOf(
            Triple("BUKU", onBukuClick, R.drawable.buku3),
            Triple("KATEGORI", onKategoriClick, R.drawable.kategori2),
            Triple("PENERBIT", onPenerbitClick, R.drawable.penerbit),
            Triple("PENULIS", onPenulisClick, R.drawable.penulis)
        ).forEach { (title, action, imageResId) ->
            ElegantHorizontalCard(
                title = title,
                action = action,
                imageResId = imageResId,
                gradient = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF1A237E), // Biru gelap
                        Color(0xFF3949AB)  // Biru terang
                    )
                )
            )
        }
    }
}

@Composable
fun ElegantHorizontalCard(
    title: String,
    action: () -> Unit,
    imageResId: Int, // Parameter untuk gambar
    gradient: Brush,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { action() }
            .height(120.dp)
            .padding(vertical = 8.dp), // Memberikan jarak antar kartu
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp), // Mengurangi elevasi agar lebih rata
        colors = CardDefaults.cardColors(containerColor = Color.Transparent) // Menggunakan latar belakang transparan agar gradien terlihat
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart // Mengubah alignment ke kiri
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                // Gambar dari resource dengan ukuran lebih besar
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = title,
                    modifier = Modifier
                        .size(80.dp) // Memperbesar ukuran gambar
                        .padding(end = 16.dp), // Memberikan jarak antara gambar dan teks
                    contentScale = ContentScale.Crop // Memastikan gambar terpotong dengan baik
                )
                // Judul
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontSize = 20.sp
                    ),
                    modifier = Modifier.padding(end = 16.dp) // Memberikan jarak antar teks dan elemen lainnya
                )
            }
        }
    }
}
