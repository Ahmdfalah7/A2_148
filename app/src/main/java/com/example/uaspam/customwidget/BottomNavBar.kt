package com.example.uaspam.customwidget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuButton(
    modifier: Modifier = Modifier,
    onBukuClick: () -> Unit,
    onKategoriClick: () -> Unit,
    onPenerbitClick: () -> Unit,
    onPenulisClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF6A1B9A), Color(0xFF3949AB)) // Gradasi ungu ke biru
                ),
                shape = RoundedCornerShape(16.dp) // Sudut lebih halus
            )
            .padding(8.dp)
    ) {
        // Tombol Buku
        IconButton(onClick = onBukuClick) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Filled.MailOutline,
                    contentDescription = "Buku",
                    tint = Color.White,
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.2f), CircleShape)
                        .padding(8.dp)
                )
                Text(
                    text = "Buku",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }

        // Tombol Kategori
        IconButton(onClick = onKategoriClick) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Filled.List,
                    contentDescription = "Kategori",
                    tint = Color.White,
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.2f), CircleShape)
                        .padding(8.dp)
                )
                Text(
                    text = "Kategori",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }

        // Tombol Penerbit
        IconButton(onClick = onPenerbitClick) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Filled.AccountBox,
                    contentDescription = "Penerbit",
                    tint = Color.White,
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.2f), CircleShape)
                        .padding(8.dp)
                )
                Text(
                    text = "Penerbit",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }

        // Tombol Penulis
        IconButton(onClick = onPenulisClick) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Penulis",
                    tint = Color.White,
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.2f), CircleShape)
                        .padding(8.dp)
                )
                Text(
                    text = "Penulis",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }
    }
}
