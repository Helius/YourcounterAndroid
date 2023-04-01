package com.ghelius.yourcounter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class SmsBroadcastReceiver : BroadcastReceiver() {

    fun SmsBroadcastReceiver.goAsync(
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> Unit
    ) {
        val pendingResult = goAsync()
        CoroutineScope(SupervisorJob()).launch(context) {
            try {
                block()
            } finally {
                pendingResult.finish()
            }
        }
    }

    override fun onReceive(p0: Context?, p1: Intent?) = goAsync {
//        val repo = MyRepository.getInstance(context)
//        val alarms = repo.getAlarms() // a suspend function
        // do stuff
        Log.d(TAG, "got something");
    }

    companion object {
        private val TAG = "SMSReceiver"
    }

}