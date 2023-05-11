package com.ghelius.yourcounter.entity

import java.io.Serializable

data class Group(val name: String? = null)
data class Category(val groupId: String? = null, val name: String? = null)
data class Wallet(val fixTime: Int? = null, val fixedAmount: Int? = null, val name: String? = null)

data class Transaction(
    val id: String = "",
    val amount: Long = 0L,
    var categoryId: String = "",
    val walletId: String = "",
    val dateTime: Long = 0L,
    val comment: String = "",
    val who: String = ""
) : Serializable