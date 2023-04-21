package com.ghelius.yourcounter.presentation.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghelius.yourcounter.entity.TransactionCandidate
import com.ghelius.yourcounter.usecase.UserInputTextProcessUsecase
import kotlinx.coroutines.launch

class TestViewModel(private val usecase: UserInputTextProcessUsecase) : ViewModel() {
    fun startProcess(text: String) {
        Log.d(TAG, "got $text")
        viewModelScope.launch {
            val transaction = usecase.parseTransaction(text)
        }
    }

    companion object {
        const val TAG = "TestVM"
    }
}