package com.ghelius.yourcounter.presentation.vm

import com.ghelius.yourcounter.entity.Actions
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

class ValueToUiConverters {
    companion object {

        fun actionToString(action: Actions) : String {
            return when (action) {
                Actions.Buying -> "Покупка"
                Actions.AdvancePayment -> "Аванс"
                Actions.Salary -> "Зарплата"
                Actions.Refund -> "Возврат"
                Actions.Transfer -> "Перевод"
                Actions.Paying -> "Списание"
                Actions.ATM -> "Выдача"
                else -> "Unknown"
            }
        }

        fun amountToString(amount: Long) : String
        {
            val main = abs(amount) / 100
            val cents = abs(amount) - main*100
            assert(cents < 100)
            return "${if (amount < 0) "-" else ""}${main}.%02d".format(cents)
        }

        fun formatDateTime(ts: Long): String {
            return try {
                val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.US)
                val netDate = Date(ts)
                sdf.timeZone = TimeZone.getDefault()
                sdf.format(netDate)
            } catch (e: Exception) {
                e.toString()
            }
        }
    }
}