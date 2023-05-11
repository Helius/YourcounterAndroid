package com.ghelius.yourcounter.presentation.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghelius.yourcounter.data.SmsParser
import com.ghelius.yourcounter.entity.TransactionCandidate
import com.ghelius.yourcounter.usecase.SmsReceiveUsecase
import com.ghelius.yourcounter.usecase.UserInputTextProcessUsecase
import kotlinx.coroutines.launch

class TestViewModel(private val usecase: SmsReceiveUsecase) : ViewModel() {
    fun startProcess(text: String) {
        Log.d(TAG, "got $text")
        viewModelScope.launch {
            val parser = SmsParser()
            val transactionCandidate = parser.parse(text)
            usecase.processTransactionCandidate(transactionCandidate)
        }
    }

    companion object {
        const val TAG = "TestVM"
    }
}