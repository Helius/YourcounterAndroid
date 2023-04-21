package com.ghelius.yourcounter.data

import android.Manifest
import android.app.Notification
import android.app.Notification.EXTRA_NOTIFICATION_ID
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ghelius.yourcounter.AddTransactionActionReceiver
import com.ghelius.yourcounter.MainActivity
import com.ghelius.yourcounter.entity.Actions
import com.ghelius.yourcounter.entity.Transaction
import com.ghelius.yourcounter.entity.TransactionCandidate

class NotificationService(private val context: Context) {

    init {
        createNotificationChannel()
    }

    private fun actionToString(action : Actions) : String {
        return when (action) {
            Actions.Buying -> "Покупка"
            Actions.AdvancePayment -> "Аванс"
            Actions.Salary -> "Зарплата"
            Actions.Refund -> "Возврат"
            Actions.Transfer -> "Перевод"
            Actions.Paying -> "Списание"
            Actions.ATM -> "Выдача"
        }
    }

    private fun amountToString(amount : Long) : String {
        val main = amount / 100
        return "${main}.${amount - main*100}"
    }

    fun showNotification(transactionCandidate : TransactionCandidate, transaction: Transaction, categoryName: String) {
        val title = "${actionToString(transactionCandidate.action)}: ${amountToString(transaction.amount)} RUB"
        val text = "${transactionCandidate.dest} : $categoryName"

        val notification = buildNotification(title, text, transaction, transactionCandidate)
        val rnd : Int = (0..100).random()
        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.e(TAG, "Don't have notification permission")
                return
            }
            notify(rnd, notification)
        }
    }

    private fun buildNotification(title: String, text: String, transaction: Transaction, transactionCandidate: TransactionCandidate) : Notification {

        // Create an explicit intent for an Activity in your app
        val screenIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(EXTRA_NOTIFICATION_ID, 0)
            putExtra("transaction", transaction)
            putExtra("transactionCandidate", transactionCandidate)
        }
        val openPendingIntent: PendingIntent =
            PendingIntent.getActivity(context, 0, screenIntent, PendingIntent.FLAG_IMMUTABLE)

        val addIntent = Intent(context, AddTransactionActionReceiver::class.java).apply {
//            action = ACTION_AD
            putExtra(EXTRA_NOTIFICATION_ID, 0)
            putExtra("transaction", transaction)
            putExtra("transactionCandidate", transactionCandidate)
        }
        val addPendingIntent : PendingIntent =
            PendingIntent.getActivity(context, 0, addIntent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.star_on)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(openPendingIntent)
            .addAction(android.R.drawable.ic_menu_add, "ADD",
                addPendingIntent)
            .setAutoCancel(true)
        return builder.build()
    }

    private fun createNotificationChannel() {
        val name = CHANNEL_NAME
        val descriptionText = CHANNEL_DESC
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        const val CHANNEL_ID = "42"
        const val CHANNEL_NAME = "Transaction"
        const val CHANNEL_DESC = "Adding transaction channel"

        const val TAG = "NotificationService"
    }
}