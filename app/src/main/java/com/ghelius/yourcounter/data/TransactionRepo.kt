package com.ghelius.yourcounter.data

import android.util.Log
import com.ghelius.yourcounter.entity.Transaction
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

class TransactionRepo {
    private val databaseReference = FirebaseDatabase.getInstance().getReference("transaction")
    private val _transactions : MutableStateFlow<List<Transaction>> = MutableStateFlow(emptyList())
    val transactions : StateFlow<List<Transaction>> = _transactions

    init {
        //TODO: add child listener with onChildAdded
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val list : MutableList<Transaction> = mutableListOf()
                dataSnapshot.children.forEach {
                    val key = it.key
                    val transaction = it.getValue<Transaction>()
                    if (key != null && transaction != null) {
                        list.add(transaction)
                    }
                }
                list.reverse()
                _transactions.value = list
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        databaseReference.limitToLast(20).addValueEventListener(listener)
    }

    suspend fun addTransaction(transaction: Transaction) : String {
        val ref = databaseReference.push()
        ref.setValue(transaction).await()
        return ref.key!!
    }

    suspend fun deleteTransaction(id: String) {
        databaseReference.child(id).removeValue().await()
    }

    companion object {
        const val TAG = "TransactionRepo"
    }
}