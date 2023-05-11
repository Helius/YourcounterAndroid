package com.ghelius.yourcounter.presentation.vm

import com.ghelius.yourcounter.entity.TransactionCandidate

class NotificationViewModel(
    private val candidate: TransactionCandidate,
    private val categoryName: String
) {
    val title: String
        get() {
            return "${ValueToUiConverters.actionToString(candidate.action)}: ${
                ValueToUiConverters.amountToString(
                    candidate.amount
                )
            }"
        }
    val text: String
        get() {
            return "${candidate.dest} : $categoryName"
        }
}