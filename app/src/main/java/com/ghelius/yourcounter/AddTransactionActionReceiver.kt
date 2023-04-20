package com.ghelius.yourcounter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AddTransactionActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (context!= null && intent != null) {
           //TODO: just add transaction to DB
        }
    }
}