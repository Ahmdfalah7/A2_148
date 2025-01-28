package com.example.uaspam

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.uaspam.ui.theme.UasPAMTheme
import com.example.uaspam.ui.viewmodel.BukuApp

class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UasPAMTheme {
                // Tidak perlu lagi membuat NavController di sini
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BukuApp(modifier = Modifier.padding(innerPadding)) // Hanya oper modifier
                }
            }
        }
    }
}

