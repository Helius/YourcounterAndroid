package com.ghelius.yourcounter.usecase

import com.ghelius.yourcounter.data.NotificationService
import com.ghelius.yourcounter.data.SmsParser
import com.ghelius.yourcounter.entity.Transaction
import com.ghelius.yourcounter.entity.TransactionCandidate

class UserInputTextProcessUsecase(private val notificationService: NotificationService) {
    private val parser = SmsParser()

    fun parseTransaction(text: String) : TransactionCandidate {
        return parser.parse(text)
    }

    suspend fun processUserInput(text: String) {

        val candidate = parser.parse(text)
        val transaction = Transaction(amount = candidate.amount)
        val categoryName = "HZ"

        notificationService.showNotification(candidate, transaction, categoryName)
    }
}