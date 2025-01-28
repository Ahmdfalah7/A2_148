package com.example.uaspam.customwidget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uaspam.R

@Composable
fun TopHomeAppBar(
    onBack: () -> Unit,
    showBackButton: Boolean = true,
    modifier: Modifier = Modifier
) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF001F3F), // Warna navy yang digunakan di HomeAppView
            Color(0xFF3949AB)  // Biru terang yang serasi
        )
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(gradient, shape = RoundedCornerShape(bottomEnd = 40.dp))
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Tombol kembali
            if (showBackButton) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f))
                        .padding(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            } else {
                Spacer(modifier = Modifier.width(48.dp)) // Placeholder untuk menjaga alignment
            }

            // Informasi utama
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = if (showBackButton) 12.dp else 0.dp)
            ) {
                Text(
                    text = "Perpustakaan",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                )
                Text(
                    text = "Perpustakaan University",
                    color = Color.White.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                )
            }

            // Gambar/logo tambahan di sisi kanan
            Icon(
                painter = painterResource(id = R.drawable.download), // Pastikan file ada di res/drawable
                contentDescription = "Logo Perpustakaan",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f)),
                tint = Color.Unspecified
            )
        }
    }
}
