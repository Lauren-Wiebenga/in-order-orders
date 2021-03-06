package lw.inorderorders

import lw.inorderorders.service.CashRegister
import lw.inorderorders.service.Product
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
            val input = "FOO,BAR,APPLE,ORANGE,APPLE"

            val output = CashRegister.getValidProducts(input)

            assertThat(output).containsExactlyInAnyOrderElementsOf(listOf(Product.ORANGE, Product.APPLE, Product.APPLE))
        }

        @Test
        fun `getValidProducts handles empty string`() {
            val input = ""

            val output = CashRegister.getValidProducts(input)

            assertThat(output).isEmpty()
        }
    }

    @Nested
    inner class DetermineDiscount {
        @Test
        fun `two apples produce single apple discount`() {
            val input = listOf(Product.APPLE, Product.APPLE)

            val output = CashRegister.determineDiscount(input)

            assertEquals(0.6, output)
        }

        @Test
        fun `three apples produce single apple discount`() {
            val input = listOf(Product.APPLE, Product.APPLE, Product.APPLE)

            val output = CashRegister.determineDiscount(input)

            assertEquals(0.6, output)
        }

        @Test
        fun `three oranges produce single orange discount`() {
            val input = listOf(Product.ORANGE, Product.ORANGE, Product.ORANGE)

            val output = CashRegister.determineDiscount(input)

            assertEquals(0.25, output)
        }

        @Test
        fun `five oranges produce single orange discount`() {
            val input = listOf(Product.ORANGE, Product.ORANGE, Product.ORANGE, Product.ORANGE, Product.ORANGE)

            val output = CashRegister.determineDiscount(input)

            assertEquals(0.25, output)
        }

        @Test
        fun `siz oranges produce two orange discount`() {
            val input = listOf(Product.ORANGE, Product.ORANGE, Product.ORANGE, Product.ORANGE, Product.ORANGE, Product.ORANGE)

            val output = CashRegister.determineDiscount(input)

            assertEquals(0.5, output)
        }

        @Test
        fun `determines discount for both product types at once`() {
            val input = listOf(Product.ORANGE, Product.ORANGE, Product.ORANGE, Product.APPLE, Product.APPLE)

            val output = CashRegister.determineDiscount(input)

            assertEquals(0.85, output)
        }
    }

    @Nested
    inner class Checkout {
        @Test
        fun `checkout returns correct total`() {
            val input = listOf(Product.APPLE, Product.APPLE, Product.ORANGE, Product.APPLE)

            val output = CashRegister.checkout(input)

            assertEquals("$1.45", output)
        }
    }
}