package com.ghelius.yourcounter.entity

import java.io.Serializable

data class Group(val name: String? = null)
data class Category(val groupId: String? = null, val name: String? = null)
data class Wallet(val fixTime: Int? = null, val fixedAmount: Int? = null, val name: String? = null)

data class Transaction(
    val id: String? = null,
    val amount: Long,
    val categoryId: String? = null,
    val walletId: String? = null,
    val dateTime: Long? = null,
    val comment: String? = null
) : Serializable