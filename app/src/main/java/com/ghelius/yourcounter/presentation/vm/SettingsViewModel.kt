package com.ghelius.yourcounter.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghelius.yourcounter.data.CategoryBindingRepo
import com.ghelius.yourcounter.data.CategoryRepo
import com.ghelius.yourcounter.usecase.BindingReceiversUsecase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class BindingViewModel(val receiver: String, val categories : List<String>)

class SettingsViewModel(private val bindingRepo: CategoryBindingRepo, private val categoryRepo: CategoryRepo) : ViewModel() {

    private val _bindingList : MutableStateFlow<List<BindingViewModel>> = MutableStateFlow(emptyList())
    val bindingList : StateFlow<List<BindingViewModel>> = _bindingList

    init {
//        viewModelScope.launch {
//            bindingRepo.bindingListFlow.collect {
//                var list: MutableList<BindingViewModel> = mutableListOf()
//                for (b in it) {
//                    val categoryNames: MutableList<String> = mutableListOf()
//                    for (categoryId in b.categoriesId) {
//                        val name = categoryRepo.categoriesById.value[categoryId]?.name
//                        categoryNames.add(name?: categoryId)
//                    }
//                    list.add(BindingViewModel(b.receiver, categoryNames))
//                }
//                _bindingList.value = list
//            }
//        }
    }

//    fun addBinding(receiver: String, categoryId: String) {
//        viewModelScope.launch {
//            bindingUsecase.addBinding(receiver, categoryId)
//        }
//    }

}