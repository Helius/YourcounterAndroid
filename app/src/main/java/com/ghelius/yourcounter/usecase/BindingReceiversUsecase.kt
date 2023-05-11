package com.ghelius.yourcounter.usecase

import com.ghelius.yourcounter.data.CategoryBindingRepo

class BindingReceiversUsecase (private val bindingRepo : CategoryBindingRepo) {

    val bindingListFlow = bindingRepo.bindings

    suspend fun addBinding(receiver: String, categoryId: String) {
        bindingRepo.addCategoryFor(receiver, categoryId)
    }

}