package lw.inorderorders

import lw.inorderorders.service.CashRegister
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CashRegisterTests {

    @Nested
    inner class GetOrderItems {
        @Test
        fun `getOrderItems removes whitespace`() {
            val input = "    FOO,    BAR     ,   LAA"

            val output = CashRegister.getOrderItems(input)

            Assertions.assertThat(output).containsExactlyInAnyOrderElementsOf(listOf("FOO", "BAR", "LAA"))
        }

        @Test
        fun `getOrderItems capitalizes all values`() {
            val input = "fOo,BaR,lAA"

            val output = CashRegister.getOrderItems(input)

            Assertions.assertThat(output).containsExactlyInAnyOrderElementsOf(listOf("FOO", "BAR", "LAA"))
        }

        @Test
        fun `getOrderItems handles null input`() {
            val input = null

            val output = CashRegister.getOrderItems(input)

            Assertions.assertThat(output).isEmpty()
        }
    }

    @Nested
    inner class CalculateCharge {
        @Test
        fun `calculateCharge returns correct prices`() {
            val appleInput = "APPLE"

            val appleOutput = CashRegister.calculateCharge(appleInput)

            assertEquals(0.6, appleOutput)

            val orangeInput = "ORANGE"

            val orangeOutput = CashRegister.calculateCharge(orangeInput)

            assertEquals(0.25, orangeOutput)

            val badInput = "BLAHBLAHBLAH"

            val badOutput = CashRegister.calculateCharge(badInput)

            assertEquals(0.0, badOutput)
        }
    }

    @Nested
    inner class Checkout {
        @Test
        fun `checkout returns correct total`() {
            val input = "apple,apple,orange,apple"

            val output = CashRegister.checkout(input)

            assertEquals("$2.05", output)
        }
    }
}