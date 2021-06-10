package lw.inorderorders.service

class OrderService {
    companion object {
        fun takeOrder() {
            var exit = false;
            while (!exit) {
                println("""
                    Welcome!
                    Enter your order:
                """.trimIndent())

                val order = readLine()

                println("You owe ${CashRegister.checkout(order)}")

                println("Would you like to exit?(y/n)")
                val exitAnswer = readLine()
                exit = exitAnswer?.toUpperCase() == "Y"
            }
        }
    }
}