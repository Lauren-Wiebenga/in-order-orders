package lw.inorderorders

import lw.inorderorders.service.CashRegister
import lw.inorderorders.service.Product
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CashRegisterTests {

    @Nested
    inner class SplitOrder {
        @Test
        fun `splitOrder removes whitespace`() {
            val input = "    FOO,    BAR     ,   LAA"

            val output = CashRegister.splitOrder(input)

            assertThat(output).containsExactlyInAnyOrderElementsOf(listOf("FOO", "BAR", "LAA"))
        }

        @Test
        fun `splitOrder capitalizes all values`() {
            val input = "fOo,BaR,lAA"

            val output = CashRegister.splitOrder(input)

            assertThat(output).containsExactlyInAnyOrderElementsOf(listOf("FOO", "BAR", "LAA"))
        }

        @Test
        fun `splitOrder handles null input`() {
            val input = null

            val output = CashRegister.splitOrder(input)

            assertThat(output).isEmpty()
        }
    }

    @Nested
    inner class GetValidProducts {

        @Test
        fun `getValidProducts removes invalid items`() {
            val input = listOf("FOO", "BAR", "APPLE", "ORANGE", "APPLE")

            val output = CashRegister.getValidProducts(input)

            assertThat(output).containsExactlyInAnyOrderElementsOf(listOf(Product.ORANGE, Product.APPLE, Product.APPLE))
        }

        @Test
        fun `getValidProducts handles empty list`() {
            val input = emptyList<String>()

            val output = CashRegister.getValidProducts(input)

            assertThat(output).isEmpty()
        }
    }

    @Nested
    inner class DetermineDiscount {
        @Test
        fun `two apples cost the same as one`() {
            val input = listOf(Product.APPLE, Product.APPLE)

            val output = CashRegister.determineDiscount(input)

            assertEquals(0.6, output)
        }

        @Test
        fun `three apples only apply discount for two apples`() {
            val input = listOf(Product.APPLE, Product.APPLE, Product.APPLE)

            val output = CashRegister.determineDiscount(input)

            assertEquals(0.6, output)
        }

        @Test
        fun `three oranges cost the same as two`() {
            val input = listOf(Product.ORANGE, Product.ORANGE, Product.ORANGE)

            val output = CashRegister.determineDiscount(input)

            assertEquals(0.5, output)
        }

        @Test
        fun `five oranges only apply discount for three`() {
            val input = listOf(Product.ORANGE, Product.ORANGE, Product.ORANGE, Product.ORANGE, Product.ORANGE)

            val output = CashRegister.determineDiscount(input)

            assertEquals(0.5, output)
        }

        @Test
        fun `determines discount for both product types at once`() {
            val input = listOf(Product.ORANGE, Product.ORANGE, Product.ORANGE, Product.APPLE, Product.APPLE)

            val output = CashRegister.determineDiscount(input)

            assertEquals(1.1, output)
        }
    }

    @Nested
    inner class Checkout {
        @Test
        fun `checkout returns correct total`() {
            val input = "apple,apple,orange,apple"

            val output = CashRegister.checkout(input)

            assertEquals("$1.45", output)
        }
    }
}