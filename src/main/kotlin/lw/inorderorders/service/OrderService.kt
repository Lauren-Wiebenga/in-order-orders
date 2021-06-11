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

        var exit = false;
        while (!exit) {
            println("""
            Welcome!
            Enter your order:
        """.trimIndent())

            val order = readLine()
            val charge = CashRegister.checkout(order)

            println("You owe $charge")

            template.convertAndSend("""
                
                Your order has shipped!
                
                Would you like to exit?(y/n)
            """.trimIndent())

            exit = readLine()?.toUpperCase() == "Y"
        }
    }
}