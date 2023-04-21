package com.ghelius.yourcounter

import com.ghelius.yourcounter.data.SmsParser
import com.ghelius.yourcounter.entity.Actions
import junit.framework.TestCase.*
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class SmsParserUnitTest {

    @Test
    fun parse_time() {
        val data = arrayListOf(
            "08:45",
            "00:00",
            "23:23",
        )
        val parser = SmsParser()

        val datetime0 = parser.parseTime(data[0]);
        assertEquals(8, datetime0.hour)
        assertEquals(45, datetime0.minute)

        val datetime1 = parser.parseTime(data[1]);
        assertEquals(0, datetime1.hour)
        assertEquals(0, datetime1.minute)

        val datetime2 = parser.parseTime(data[2]);
        assertEquals(23, datetime2.hour)
        assertEquals(23, datetime2.minute)
    }

    @Test
    fun parse_amount() {
        val data = arrayListOf(
            "481",
            "481,12",
            "481.12"
        )
        val parser = SmsParser()
        assertEquals(parser.parseAmount(data[0]), 48100)
        assertEquals(parser.parseAmount(data[1]), 48112)
        assertEquals(parser.parseAmount(data[2]), 48112)
    }

    @Test
    fun parse_buying() {
        val parser = SmsParser()
        try {
            val transaction = parser.parse(data[0]);
            assertEquals(transaction.acc, "ECMC8283")
            assertEquals(transaction.action, Actions.Buying)
            assertEquals(transaction.amount, -48174)
            assertEquals(transaction.total, 194146)
            assertEquals(transaction.dest, "BYSTRONOM")

            val date = LocalDate.now();
            val time = LocalTime.of(16, 22)

            assertEquals(date.atTime(time), transaction.dateTime)


        } catch (e : java.lang.Exception) {
            assertTrue("Can't parse sms: ${e.message}", false)
        }

        try {
            val transaction = parser.parse(data[1]);
            assertEquals(transaction.amount, -104530)
            assertEquals(transaction.acc, "MIR-2924")
            assertEquals(transaction.dest, "ALIEXPRESS")

        } catch (e : java.lang.Exception) {
            assertTrue("Can't parse sms: ${e.message}", false)
        }
    }

    @Test
    fun parse_income() {
        val parser = SmsParser()
        try {
            val transaction = parser.parse(data[2]);
            assertEquals(transaction.acc, "ECMC8283")
            assertEquals(transaction.action, Actions.AdvancePayment)
            assertEquals(transaction.amount, 1425875)
            assertEquals(transaction.total, 3373021)
            assertEquals(transaction.dest, "")
        } catch (e: java.lang.Exception) {
            assertTrue("Can't parse transaction: ${e.message}", false)
        }

        try {
            val transaction = parser.parse(data[3]);
            assertEquals(transaction.acc, "ECMC8283")
            assertEquals(transaction.action, Actions.Salary)
            assertEquals(transaction.amount, 1516448)
            assertEquals(transaction.total, 2461189)
            assertEquals(transaction.dest, "")
        } catch (e: java.lang.Exception) {
            assertTrue("Can't parse transaction: ${e.message}", false)
        }

        try {
            val transaction = parser.parse(data[4]);
            assertEquals(transaction.acc, "ECMC8283")
            assertEquals(transaction.action, Actions.Refund)
            assertEquals(transaction.amount, 3408)
            assertEquals(transaction.total, 2029120)
            assertEquals(transaction.dest, "AZS OPTI")
        } catch (e: java.lang.Exception) {
            assertTrue("Can't parse transaction: ${e.message}", false)
        }
    }

    @Test
    fun parse_paying() {
        val parser = SmsParser()
        try {
            val transaction = parser.parse(data[5]);
            assertEquals(transaction.acc, "ECMC8283")
            assertEquals(transaction.action, Actions.Paying)
            assertEquals(transaction.amount,-814386)
            assertEquals(transaction.total, 2947554)
            assertEquals(transaction.dest, "за кредит")
        } catch (e: java.lang.Exception) {
            assertTrue("Can't parse transaction: ${e.message}", false)
        }

        try {
            val transaction = parser.parse(data[6]);
            assertEquals(transaction.acc, "ECMC8283")
            assertEquals(transaction.action, Actions.Paying)
            assertEquals(transaction.amount,-25000)
            assertEquals(transaction.total, 2235295)
            assertEquals(transaction.dest, "Автоплатёж рег. 250 мне ТЕЛЕ2")
        } catch (e: java.lang.Exception) {
            assertTrue("Can't parse transaction: ${e.message}", false)
        }

        try {
            val transaction = parser.parse(data[7]);
            assertEquals(transaction.acc, "ECMC8283")
            assertEquals(transaction.action, Actions.Paying)
            assertEquals(transaction.amount,-7000)
            assertEquals(transaction.total,3546223 )
            assertEquals(transaction.dest, "за уведомления. Следующее списание 17.04.23.")
        } catch (e: java.lang.Exception) {
            assertTrue("Can't parse transaction: ${e.message}", false)
        }
    }

    @Test
    fun parse_transfer() {
        val parser = SmsParser()
        try {
            val transaction = parser.parse(data[8]);
            assertEquals(transaction.acc, "ECMC8283")
            assertEquals(transaction.action, Actions.Transfer)
            assertEquals(transaction.amount, -5000)
            assertEquals(transaction.total, 3686021)
            assertEquals(transaction.dest, "")
        } catch (e: java.lang.Exception) {
            assertTrue("Can't parse transaction: ${e.message}", false)
        }
    }

    @Test
    fun parse_atm() {
        val parser = SmsParser()
        try {
            val transaction = parser.parse(data[9]);
            assertEquals(transaction.acc, "ECMC8283")
            assertEquals(transaction.action, Actions.ATM)
            assertEquals(transaction.amount, -140000)
            assertEquals(transaction.total, 3328940)
            assertEquals(transaction.dest, "ATM 60027701")
        } catch (e: java.lang.Exception) {
            assertTrue("Can't parse transaction: ${e.message}", false)
        }
    }

    companion object {
        val data = arrayListOf<String>(
            "ECMC8283 16:22 Покупка 481.74р BYSTRONOM Баланс: 1941.46р",
            "MIR-2924 06:28 Покупка 1045.30р ALIEXPRESS Баланс: 16412.95р",

            "ECMC8283 05:36 Зачисление аванса 14258.75р Баланс: 33730.21р",
            "ECMC8283 05:57 Зачисление зарплаты 15164.48р Баланс: 24611.89р",
            "ECMC8283 08:56 Отмена покупки 34.08р AZS OPTI Баланс: 20291.20р",

            "ECMC8283 08:23 списание 8143.86р за кредит Баланс: 29475.54р",
            "ECMC8283 06:07 Оплата 250р Автоплатёж рег. 250 мне ТЕЛЕ2 Баланс: 22352.95р",
            "ECMC8283 01:13 Оплата 70р за уведомления. Следующее списание 17.04.23. Баланс 35462,23р",

            "ECMC8283 07:27 перевод 50р Баланс: 36860.21р",
            "ECMC8283 08:45 Выдача 1400р ATM 60027701 Баланс: 33289.40р",

            //TODO: not implemented yet
            "Накопительный счет *7674 Капитализация на 27,30р. Баланс: 1011,06р"
        )
    }
}