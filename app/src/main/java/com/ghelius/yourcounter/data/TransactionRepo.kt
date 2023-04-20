package com.ghelius.yourcounter.data

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class TransactionRepo {
    private var database: DatabaseReference = Firebase.database.reference
    fun addTransaction() {
        TODO("Not yet implemented")
    }
}