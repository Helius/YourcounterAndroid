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
    ATM,
    Unknown
}

data class TransactionCandidate(
    val acc: String = "",
    val action: Actions = Actions.Unknown,
    val amount: Long = 0L,
    val total: Long = 0L,
    val dest: String = "",
    val dateTime: Long = 0L
) : Serializable
{
    override fun toString() : String {
        return "From $acc action ${action.toString()} $amount to $dest total $total at $dateTime"
    }

    fun isValid() : Boolean {
        return acc.isNotEmpty()
                && action != Actions.Unknown
                && amount != 0L
                && total != 0L
                && dest.isNotEmpty()
    }
}
