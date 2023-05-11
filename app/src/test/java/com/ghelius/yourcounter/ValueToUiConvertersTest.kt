package com.ghelius.yourcounter

import com.ghelius.yourcounter.presentation.vm.ValueToUiConverters
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ValueToUiConvertersTest {

    @Test
    fun amountToStringTest() {
        val conv = ValueToUiConverters
        assertEquals("-100.00", conv.amountToString(-10000))
        assertEquals("-100.01", conv.amountToString(-10001))
        assertEquals("-0.01", conv.amountToString(-1))
        assertEquals("100.00", conv.amountToString(10000))
        assertEquals("100.01", conv.amountToString(10001))
        assertEquals("0.01", conv.amountToString(1))
    }
}