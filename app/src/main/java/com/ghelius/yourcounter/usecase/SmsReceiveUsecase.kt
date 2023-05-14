package com.ghelius.yourcounter.usecase

import com.ghelius.yourcounter.data.*
import com.ghelius.yourcounter.entity.TransactionCandidate

class SmsReceiveUsecase(
    private val categoryBindingRepo: CategoryBindingRepo,
    private val categoryRepo: CategoryRepo,
    private val candidatesRepo: TransactionCandidateRepo,
    private val notificationService: NotificationService
) {

    suspend fun processTransactionCandidate(transactionCandidate: TransactionCandidate) {
        val categoryIds = categoryBindingRepo.getCategoryIdsForDest(transactionCandidate.dest)
        //TODO: val walletId = ...
        var categoryName = String()
        if (categoryIds.isEmpty()) {
            categoryName = "Can't guess"
        } else {
            for (id in categoryIds) {
                val name = categoryRepo.categoriesById.value[id]?.name ?: id
                categoryName += "$name "
            }
        }

        val id = candidatesRepo.addCandidate(transactionCandidate)

        notificationService.showNotification(
            transactionCandidate = transactionCandidate,
            categoryName = categoryName,
            categoryIds.size == 1,
            id
        )
    }
}