package lw.inorderorders.service

import java.text.NumberFormat
import java.util.Locale

enum class Price(val amount: Double) {
    APPLE(0.60),
    ORANGE(0.25)
}

class CashRegister {
    companion object {
        fun checkout(order: String?): String {

            var sum = 0.0

            getOrderItems(order).forEach { value ->
                sum += calculateCharge(value)
            }

            return formatCurrency(sum)
        }

        fun getOrderItems(order: String?): List<String> =
                order?.split(",")?.map { item -> item.trim().toUpperCase() } ?: emptyList()

        fun calculateCharge(product: String) = when (product) {
            Price.APPLE.name -> Price.APPLE.amount
            Price.ORANGE.name -> Price.ORANGE.amount
            else -> 0.0
        }

        private fun formatCurrency(amount: Double): String =
                NumberFormat.getCurrencyInstance(Locale("en", "US")).format(amount)
    }
}

