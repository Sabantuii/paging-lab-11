package com.example.lr_11_jetcom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lr_11_jetcom.ui.screens.PostListPagingScreen
import com.example.lr_11_jetcom.ui.screens.PostListScreen
import com.example.lr_11_jetcom.ui.theme.LR_11_JetComTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LR_11_JetComTheme {

                PostListPagingScreen()
            }
        }
    }
}