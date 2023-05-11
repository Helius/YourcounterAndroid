package com.ghelius.yourcounter.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ghelius.yourcounter.presentation.vm.NewViewModel
import com.ghelius.yourcounter.presentation.vm.ValueToUiConverters

@Composable
fun NewScreen(newViewModel: NewViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        CandidatesList(newViewModel = newViewModel)
        Surface (modifier = Modifier.padding(vertical = 6.dp)) {
            Text(text = "History", fontSize = 25.sp, )
        }
        LastTransactionList(newViewModel)
    }
}
@Composable
fun CandidatesList(newViewModel: NewViewModel) {
    val candidateList = newViewModel.candidates.collectAsState()
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(candidateList.value) { candidate ->
            Column(Modifier.padding(vertical = 5.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = ValueToUiConverters.actionToString(candidate.action))
                    Text(text = candidate.acc)
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = candidate.dest)
                    Text(
                        text = ValueToUiConverters.amountToString(candidate.amount),
                    )
                    Text(text = "Total: ${ValueToUiConverters.amountToString(candidate.total)}")
                }
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(Color.Yellow),
                    ) {
                        Text(text = "Продукты")
                    }
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(Color.Green),
                    ) {
                        Text(text = "Фастфуд")
                    }
                }
            }
        }
    }
}


@Composable
fun LastTransactionList(newViewModel: NewViewModel) {
    val transactionList = newViewModel.transaction.collectAsState()
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(transactionList.value) { transaction ->
            Column(modifier = Modifier
                .fillMaxWidth().padding(vertical = 3.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = newViewModel.categoryById(transaction.categoryId),
                        modifier = Modifier.fillMaxWidth(0.7f)
                    )
                    Text(text = ValueToUiConverters.amountToString(transaction.amount))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = transaction.who, fontSize = 14.sp)
                    if (transaction.comment.isNotEmpty()) {
                        Text(text = "•")
                        Text(text = transaction.comment, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}
