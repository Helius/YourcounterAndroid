package com.ghelius.yourcounter.data

import android.util.Log
import com.ghelius.yourcounter.entity.TransactionCandidate
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await


class TransactionCandidateRepo {

    private val databaseReference = FirebaseDatabase.getInstance().getReference("transactionCandidates")
    private val _candidates : MutableStateFlow<List<TransactionCandidate>> = MutableStateFlow(emptyList())
    val candidates : StateFlow<List<TransactionCandidate>> = _candidates

    init {
        //TODO: add child listener with onChildAdded
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val list : MutableList<TransactionCandidate> = mutableListOf()
                dataSnapshot.children.forEach {
                    val key = it.key
                    val candidate = it.getValue<TransactionCandidate>()
                    if (key != null && candidate != null) {
                        if (candidate.isValid()) {
                            list.add(candidate)
                        } else {
                            Log.e(TAG, "got invalid candidate from DB $candidate")
                        }
                    }
                }
                list.reverse()
                _candidates.value = list
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        databaseReference.addValueEventListener(listener)
    }

    suspend fun addCandidate(transactionCandidate: TransactionCandidate) : String {
        val ref = databaseReference.push()
        ref.setValue(transactionCandidate).await()
        return ref.key!!
    }

    suspend fun deleteCandidate(id: String) {
        databaseReference.child(id).removeValue().await()
    }

    companion object {
        const val TAG = "TransactionCandidateRepo"
    }
}