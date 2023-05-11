package com.ghelius.yourcounter.presentation.vm

import androidx.lifecycle.ViewModel
import com.ghelius.yourcounter.data.CategoryRepo
import com.ghelius.yourcounter.data.TransactionCandidateRepo
import com.ghelius.yourcounter.data.TransactionRepo

data class CandidateViewModel(val text: String)

class NewViewModel(
    private val candidateRepo: TransactionCandidateRepo,
    private val transactionRepo: TransactionRepo,
    private val categoryRepo: CategoryRepo
) : ViewModel() {
    val candidates = candidateRepo.candidates
    val transaction = transactionRepo.transactions

    fun categoryById(id: String) : String {
        return categoryRepo.categories.value[id]?.name?: "Unknown category"
    }
}