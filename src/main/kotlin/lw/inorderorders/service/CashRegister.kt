package lw.inorderorders.service

import java.text.NumberFormat
import java.util.Locale

enum class Product(val amount: Double) {
    APPLE(0.60),
    ORANGE(0.25)
}

class CashRegister {
    companion object {
        fun checkout(products: List<Product>): String {

            val cost = products.sumOf { it.amount } - determineDiscount(products)

            return formatCurrency(cost)
        }

        fun splitOrder(order: String?): List<String> =
                order?.split(",")?.map { item -> item.trim().toUpperCase() } ?: emptyList()


        fun getValidProducts(items: String): List<Product> =
                splitOrder(items).mapNotNull { item ->
                    when (item) {
                        Product.APPLE.name -> Product.APPLE
                        Product.ORANGE.name -> Product.ORANGE
                        else -> null
                    }
                }

        fun determineDiscount(products: List<Product>) =
                products.groupBy { it }.map { (key, value) ->
                    when (key) {
                        Product.APPLE -> (value.size / 2) * key.amount
                        Product.ORANGE -> (value.size / 3) * key.amount
                    }
                }.sum()

        private fun formatCurrency(amount: Double): String =
                NumberFormat.getCurrencyInstance(Locale("en", "US")).format(amount)
    }
}

