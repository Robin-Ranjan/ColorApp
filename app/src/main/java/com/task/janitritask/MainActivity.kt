package com.task.janitritask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.task.janitritask.ui.theme.JanitriTaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JanitriTaskTheme {
                ColorScreen(viewModel = ColorViewModal(application))
            }
        }
    }
}