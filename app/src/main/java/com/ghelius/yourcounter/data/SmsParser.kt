package com.ghelius.yourcounter.data

import com.ghelius.yourcounter.entity.Actions
import com.ghelius.yourcounter.entity.TransactionCandidate
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class SmsParser {

    // https://regex101.com/r/GTDqq7/1
    private val regex : Regex  =
        """
            ([-\w\d]+)\s(\d\d:\d\d)\s([а-яА-Я\s]+)([\.\,\d]+)р(.*)\sБаланс:?\s([\d.,]+)р
        """.trimIndent().toRegex()


    private val actions = hashMapOf<String, Actions>(
        "покупка" to Actions.Buying,
        "зачисление аванса" to Actions.AdvancePayment,
        "зачисление зарплаты" to Actions.Salary,
        "отмена покупки" to Actions.Refund,
        "перевод" to Actions.Transfer,
        "списание" to Actions.Paying,
        "оплата" to Actions.Paying,
        "выдача" to Actions.ATM
    )

    fun parse(text: String): TransactionCandidate {
        val m = regex.find(text)
        val (accStr, timeStr, actionStr, amountStr, destStr, totalStr) = m!!.destructured

        var amount = parseAmount(amountStr.trim())
        val action = actions[actionStr.trim().lowercase()]?: throw Exception("Can't parse action: $actionStr")
        when(action) {
            Actions.AdvancePayment, Actions.Salary, Actions.Refund -> Unit
            else -> amount = -amount
        }
        val total = parseAmount(totalStr.trim())
        val time = parseTime(timeStr.trim())

        return TransactionCandidate(
            acc = accStr.trim(),
            action = action,
            amount = amount,
            total = total,
            dest = destStr.trim(),
            dateTime = time
        )
    }

    fun parseAmount(text: String) : Long {
        var res : Long
        val am = text.split(".",",")
        res = am[0].toLong() * 100
        if (am.size > 1) {
            res += am[1].toInt()
        }
        return res;
    }

    fun parseTime(text: String) : LocalDateTime {
        val date = LocalDate.now()
        return date.atTime(LocalTime.parse(text))
    }
}