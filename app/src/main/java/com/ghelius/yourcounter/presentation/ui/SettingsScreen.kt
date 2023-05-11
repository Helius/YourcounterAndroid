package com.ghelius.yourcounter.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ghelius.yourcounter.entity.ReceiverCategoryBinding
import com.ghelius.yourcounter.presentation.vm.BindingViewModel
import com.ghelius.yourcounter.presentation.vm.SettingsViewModel

@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
//            .wrapContentSize(Alignment.Center)
    ) {
        BindingList(settingsViewModel = settingsViewModel)
        Text(
            text = "Settings View",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@Composable
fun BindingList(settingsViewModel: SettingsViewModel) {
    val bindingListState = settingsViewModel.bindingList.collectAsState()
    val list : List<BindingViewModel> = bindingListState.value
    Button(onClick = { settingsViewModel.addBinding("BUSTRONOM", "somecatid") }) {
        Text(text = "Add")
    }
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(list) { binding ->
            Row(Modifier.fillMaxWidth()) {
                Text(text = binding.receiver)
                Text(text = binding.categories.joinToString(), Modifier.padding(horizontal = 5.dp))
            }
        }
    }
}