package com.ghelius.yourcounter.presentation.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ghelius.yourcounter.presentation.theme.YourcounterTheme

class StartupScreen {

    @Composable
    fun startupScreen(name: String) {
        Text(text = "Hello $name!")
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        YourcounterTheme {
            startupScreen("Android")
        }
    }
}