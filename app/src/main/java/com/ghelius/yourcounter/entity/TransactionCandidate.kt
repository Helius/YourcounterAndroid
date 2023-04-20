package com.ghelius.yourcounter.entity

import java.io.Serializable
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
    val amount: Long,
    val total: Long,
    val dest: String,
    val dateTime: LocalDateTime
) : Serializable
{
    override fun toString() : String {
        return "From $acc action ${action.toString()} $amount to $dest total $total at $dateTime"
    }
}
