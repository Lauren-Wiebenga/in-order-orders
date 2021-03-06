package lw.inorderorders.service

import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service

@Service
class OrderService(
        val template: RabbitTemplate,
        val fanoutExchange: FanoutExchange
) {

    fun takeOrder() {
        template.setExchange(fanoutExchange.name)

        var exit = false
        var apples = 5
        var oranges = 16

        while (!exit) {
            println("""
            Welcome!
            Enter your order:
        """.trimIndent())

            val order = readLine()
            val products = CashRegister.getValidProducts(order ?: "")

            if (products.filter { it == Product.APPLE }.size <= apples &&
                    products.filter { it == Product.ORANGE }.size <= oranges) {

                val charge = CashRegister.checkout(products)

                apples -= products.filter { it == Product.APPLE }.size
                oranges -= products.filter { it == Product.ORANGE }.size

                println("You owe $charge")

                template.convertAndSend("""
                
                Your order has shipped!
                
                Would you like to exit?(y/n)
            """.trimIndent())
            } else {
                template.convertAndSend("""
                    
                    Unable to complete order, out of stock.
                    
                    Would you like to exit?(y/n)
                """.trimIndent())
            }

            exit = readLine()?.toUpperCase() == "Y"
        }
    }
}