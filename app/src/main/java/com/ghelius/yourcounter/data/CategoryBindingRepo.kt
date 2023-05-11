package com.ghelius.yourcounter.data

import android.util.Log
import com.ghelius.yourcounter.entity.CategoryList
import com.ghelius.yourcounter.entity.ReceiverCategoryBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await



class CategoryBindingRepo() {
    private val databaseReference = FirebaseDatabase.getInstance().getReference("receiver2categoryBindings")
    private val _bindings : MutableStateFlow<List<ReceiverCategoryBinding>> = MutableStateFlow(emptyList())
    val bindings : StateFlow<List<ReceiverCategoryBinding>> = _bindings

    init {
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val list : MutableList<ReceiverCategoryBinding> = mutableListOf()
                dataSnapshot.children.forEach {
                    val key = it.key
                    val categories = it.getValue<CategoryList>()
                    if (key != null && categories != null) {
                        list.add(ReceiverCategoryBinding(key, categories.categoryIds?: emptyList()))
                    }
                }
                _bindings.value = list
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        databaseReference.addValueEventListener(listener)
    }

    suspend fun getCategoryIdsFor(text: String) : List<String> {
        val result = getBinding(text)
        return result?.categoryIds ?: emptyList()
    }

    suspend fun addCategoryFor(text: String, categoryId: String) {
        val categoryList = getBinding(text)
        if (categoryList != null) {
            if (categoryList.categoryIds != null) {
                if (!categoryList.categoryIds.contains(categoryId)) {
                    categoryList.categoryIds.add(categoryId)
                    databaseReference.child(text).setValue(categoryList).await()

                } else {
                    Log.e(TAG, "categoryId exist in binding")
                }
                return
            }
        }
        val newCategoryList = CategoryList(mutableListOf(categoryId))
        databaseReference.child(text).setValue(newCategoryList).await()
    }

    private suspend fun getBinding(text: String) : CategoryList? {
        val sn = databaseReference.child(text).get().await()
        return sn.getValue<CategoryList>()
    }

    companion object {
        const val TAG = "CategoryMatcherRepo"
    }
}