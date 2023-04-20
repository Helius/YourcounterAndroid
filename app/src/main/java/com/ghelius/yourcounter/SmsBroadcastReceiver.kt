package com.ghelius.yourcounter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import com.ghelius.yourcounter.data.SmsParser
import com.ghelius.yourcounter.usecase.SmsReceiveUsecase
import org.koin.java.KoinJavaComponent.inject


class SmsBroadcastReceiver : BroadcastReceiver() {
    private val usecase: SmsReceiveUsecase by inject(SmsReceiveUsecase::class.java)

    private fun SmsBroadcastReceiver.goAsync(
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

    override fun onReceive(context: Context, intent: Intent) = goAsync {

        if (intent.action.equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            Log.d(TAG, "got something $intent with $context")
            val extractMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            val parser = SmsParser()
            extractMessages.forEach { smsMessage ->
                Log.v(TAG, smsMessage.displayMessageBody + " from " + smsMessage.displayOriginatingAddress)
                if (smsMessage.displayOriginatingAddress.equals(PhoneNumber)) {
                    val transaction = parser.parse(smsMessage.displayMessageBody)
                    Log.v(TAG, transaction.toString())
                    usecase.processTransactionCandidate(transaction)
                }
            }
        } else {
            Log.d(TAG, "got something but i'm not sure what: $intent")
        }
    }

    companion object {
        private val TAG = "SMSReceiver"
        private val PhoneNumber = "900"
    }

}