package com.ghelius.yourcounter.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ghelius.yourcounter.presentation.vm.TestViewModel

@Composable
fun TestScreen(viewModel: TestViewModel) {
    var userText by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize()
        .wrapContentSize(Alignment.Center)
    ) {
        OutlinedTextField(
            value = userText,
            onValueChange = {
                userText = it },
            label = { Text("Label") },
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth(),
        )
        Button(onClick = { viewModel.startProcess(userText) }, enabled = userText.isNotEmpty() )  {
            Text(text = "Process")
        }
    }
}