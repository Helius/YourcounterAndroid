package com.ghelius.yourcounter.presentation.vm

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghelius.yourcounter.auth.AuthProvider
import com.ghelius.yourcounter.data.*
import com.ghelius.yourcounter.entity.ReceiverCategoryBinding
import com.ghelius.yourcounter.entity.TransactionCandidate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class CheckedCategory(val name: String, val checked: Boolean, val categoryId: String)
data class CandidateWithBoundCategory(val candidate: TransactionCandidate, val boundCategory: List<String>, val candidateId : String)

class NewViewModel(
    private val candidateRepo: TransactionCandidateRepo,
    private val transactionRepo: TransactionRepo,
    private val categoryRepo: CategoryRepo,
    private val bindingRepo: CategoryBindingRepo,
    private val auth: AuthProvider
) : ViewModel() {
    private var currentDestination = ""
    val transaction = transactionRepo.transactions
    private val _showBindingView : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showBindingView : StateFlow<Boolean> = _showBindingView

    private var candidateList = mutableStateListOf<CandidateWithBoundCategory>()
    private val _candidateListFlow = MutableStateFlow(candidateList)
    val candidateListFlow : StateFlow<List<CandidateWithBoundCategory>> get() = _candidateListFlow

    private var checkedCategoryList = mutableStateListOf<CheckedCategory>()
    private val _checkedCategoryListFlow = MutableStateFlow(checkedCategoryList)
    val checkedCategoryListFlow: StateFlow<List<CheckedCategory>> get() = _checkedCategoryListFlow

    init {

        //TODO:
        //viewModelScope.launch {
            //auth.authenticate("", "")
        //}

        viewModelScope.launch {
            candidateRepo.candidates.collect {
                updateCandidateList(candidateRepo.candidates.value, bindingRepo.bindings.value)
            }
        }
        viewModelScope.launch {
            bindingRepo.bindings.collect {
                updateCandidateList(candidateRepo.candidates.value, bindingRepo.bindings.value)
            }
        }
    }

    private fun updateCandidateList(
        candidates: List<TransactionCandidateWithId>,
        bindings: List<ReceiverCategoryBinding>
    ) {
        val newList = mutableStateListOf<CandidateWithBoundCategory>()
        for (candidate in candidates) {
            val key = candidate.candidate.dest.ifEmpty {
                candidate.candidate.action.toString()
            }
            val binding = bindings.find {
                it.receiver == key
            }
            newList.add(CandidateWithBoundCategory(candidate.candidate, binding?.categoriesId?: listOf(), candidate.id))
        }
        // set it to flow
        _candidateListFlow.value = newList
    }

    // We can update a new item
    fun checkCategory(index: Int, value: Boolean) {
//        todoList[index] = todoList[index].copy(urgent = value)
        checkedCategoryList[index] = checkedCategoryList[index].copy(checked = value)
    }

    // We can insert a new item
    fun addRecord(titleText: String, urgency: Boolean) {
//        todoList.add(TodoItem(todoList.size, titleText, urgency))
    }

    // We can retrieve an entire new list
    fun updatelist() {
//        todoList = mutableStateListOf(fetchFromRepository())
//        _todoListFlow.value = todoList
    }

    fun categoryNameById(id: String) : String {
        return categoryRepo.categoriesById.value[id]?.name?: "Unknown category"
    }

    fun removeCandidate(id: String) {
        viewModelScope.launch {
            candidateRepo.deleteCandidate(id)
        }
    }

    fun addTransaction(candidate: CandidateWithBoundCategory) {
        TODO("Not yet implemented")
    }

    fun changeCategoryByCandidate(candidate: TransactionCandidate) {
        _showBindingView.value = true
        currentDestination = candidate.dest.ifEmpty() {
            candidate.action.name
        }
        checkedCategoryList.clear()
        viewModelScope.launch {
            val boundCategory = bindingRepo.getCategoryIdsForDest(currentDestination)
            for (cat in categoryRepo.categories.value) {
                val checked = boundCategory.contains(cat.id)
                checkedCategoryList.add(CheckedCategory(cat.category.name!!, checked, cat.id))
            }
            _checkedCategoryListFlow.value = checkedCategoryList
        }
    }
    fun applyCategoryChange() {
        _showBindingView.value = false
        //TODO: collect checked item and update in
        val boundCategory: MutableList<String> = mutableListOf()
        for (checkedCategory in checkedCategoryList) {
            if (checkedCategory.checked) {
                boundCategory.add(checkedCategory.categoryId)
            }
        }
        viewModelScope.launch {
            bindingRepo.setCategoryFor(currentDestination, boundCategory)
            currentDestination = ""
        }
    }

    companion object {
        const val TAG = "NewsViewModel"
    }

}