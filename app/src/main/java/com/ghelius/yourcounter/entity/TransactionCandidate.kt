package com.ghelius.yourcounter.entity

import java.time.LocalDateTime

enum class Actions {
    Buying,
    AdvancePayment,
    Salary,
    Refund,
    Transfer,
    Paying,
    ATM
}

data class TransactionCandidate(
    val acc: String,
    val action: Actions,
    val amount: Int,
    val total: Int,
    val dest: String,
    val dateTime: LocalDateTime
)
{
    override fun toString() : String {
        return "From $acc action ${action.toString()} $amount to $dest total $total at $dateTime"
    }
}