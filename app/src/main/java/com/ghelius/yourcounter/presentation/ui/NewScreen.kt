package com.ghelius.yourcounter.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ghelius.yourcounter.presentation.vm.NewViewModel
import com.ghelius.yourcounter.presentation.vm.ValueToUiConverters

@Composable
fun NewScreen(newViewModel: NewViewModel) {
    var openBottomSheet = newViewModel.showBindingView.collectAsState()
    if (openBottomSheet.value) {
        BottomSheetView(newViewModel)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),

    ) {
        Surface(modifier = Modifier.weight(2f)) {
            CandidatesList(newViewModel)
        }
        Surface(modifier = Modifier.padding(vertical = 6.dp)) {
            Text(text = "History", fontSize = 25.sp)
        }
        Surface(modifier = Modifier.weight(1f)) {
            LastTransactionList(newViewModel)
        }
    }
}

@Composable
fun CandidatesList(newViewModel: NewViewModel) {
    val candidateList = newViewModel.candidateListFlow.collectAsState()
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(candidateList.value) { candidate ->
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Column(Modifier.padding(vertical = 5.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(text = ValueToUiConverters.actionToString(candidate.candidate.action))
                        Text(text = candidate.candidate.acc)
                    }
                    Text(
                        text = ValueToUiConverters.formatDateTime(candidate.candidate.dateTime),
                        fontSize = 14.sp
                    )
                    if (candidate.candidate.dest.isNotEmpty()) {
                        Text(text = candidate.candidate.dest)
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = ValueToUiConverters.amountToString(candidate.candidate.amount),
                        )
                        Text(text = "Total: ${ValueToUiConverters.amountToString(candidate.candidate.total)}")
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        for (categoryId in candidate.boundCategory) {
                            Button(
                                onClick = { newViewModel.addTransaction(candidate) },
                                colors = ButtonDefaults.buttonColors(Color.Green),
                            ) {
                                Text(text = newViewModel.categoryNameById(categoryId))
                            }
                        }
                        Button(
                            onClick = { newViewModel.changeCategoryByCandidate(candidate.candidate) },
                            colors = ButtonDefaults.buttonColors(Color.Yellow),
                        ) {
                            Text(text = "Pick")
                        }
                    }
                }
                Button(onClick = { newViewModel.removeCandidate(candidate.candidateId)}) {
                    Text(text = "Del")
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = newViewModel.categoryNameById(transaction.categoryId),
                        fontSize = 20.sp
                    )
                    Text(text = ValueToUiConverters.amountToString(transaction.amount))
                }
                Row(modifier = Modifier, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = ValueToUiConverters.formatDateTime(transaction.`when`), fontSize = 14.sp)
                    Text(text="*")
                    Text(text = transaction.who, fontSize = 14.sp)
                }
                if (transaction.comment.isNotEmpty()) {
                    Text(text = transaction.comment, fontSize = 14.sp)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetView(newViewModel: NewViewModel) {
    val categoryList = newViewModel.checkedCategoryListFlow.collectAsState()
    val lazyListState = rememberLazyListState()
    // Sheet content
    ModalBottomSheet(
        onDismissRequest = { newViewModel.applyCategoryChange() },
//        sheetState = bottomSheetState,
    ) {
        LazyColumn (state = lazyListState) {
            items(items = categoryList.value, itemContent = {item ->
                ListItem(
                    headlineContent = { Text(item.name) },
                    leadingContent = {
//                        Icon(
//                            Icons.Default.Favorite,
//                            contentDescription = "Localized description"
//                        )
                        Checkbox(checked = item.checked, onCheckedChange = {
                            val index = categoryList.value.indexOf(item)
                            newViewModel.checkCategory(index, it)
                        })
                    }
                )
            })

        }
    }
}