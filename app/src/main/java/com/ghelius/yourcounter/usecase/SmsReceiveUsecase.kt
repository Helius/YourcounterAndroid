package com.ghelius.yourcounter.usecase

import com.ghelius.yourcounter.data.CategoryRepo
import com.ghelius.yourcounter.data.NotificationService
import com.ghelius.yourcounter.data.TransactionRepo
import com.ghelius.yourcounter.entity.Transaction
import com.ghelius.yourcounter.entity.TransactionCandidate

class SmsReceiveUsecase(
    private val transactionRepo: TransactionRepo,
    private val categoryRepo: CategoryRepo,
    private val notificationService: NotificationService
) {

    fun processTransactionCandidate(transactionCandidate: TransactionCandidate) {

        // 1. try to guess what category should we pick by transaction.dest
        val transaction : Transaction = Transaction(amount = transactionCandidate.amount)

        // 2. show notification with transaction and category and Add/Edit buttons
        notificationService.showNotification(
            transaction = transaction,
            transactionCandidate = transactionCandidate,
            categoryName = "Can't guess"
        )
    }
}