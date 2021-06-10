package lw.inorderorders

import lw.inorderorders.service.OrderService
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class InOrderOrdersApplication

fun main(args: Array<String>) {
    OrderService.takeOrder()
}
