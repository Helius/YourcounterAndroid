package com.ghelius.yourcounter.data

import android.util.Log
import com.ghelius.yourcounter.entity.Category
import com.ghelius.yourcounter.entity.CategoryList
import com.ghelius.yourcounter.entity.ReceiverCategoryBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

typealias CategoryMap = HashMap<String, Category>
class CategoryRepo {
    private val databaseReference = FirebaseDatabase.getInstance().getReference("categories")
    private val _categories : MutableStateFlow<CategoryMap> = MutableStateFlow(
        hashMapOf()
    )
    val categories : StateFlow<CategoryMap> = _categories

    init {
        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val map : CategoryMap = hashMapOf()
                dataSnapshot.children.forEach {
                    val key = it.key
                    val category = it.getValue<Category>()
                    if (key != null && category != null) {
                        map[key] = category
                    }
                }
                _categories.value = map
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(CategoryBindingRepo.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        databaseReference.addValueEventListener(listener)
    }
}