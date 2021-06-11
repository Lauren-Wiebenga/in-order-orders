package lw.inorderorders

import lw.inorderorders.service.OrderService
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@SpringBootApplication
class InOrderOrdersApplication : CommandLineRunner {

    @Autowired
    lateinit var orderService: OrderService

    override fun run(vararg args: String?) {
        orderService.takeOrder()
    }
}

fun main(args: Array<String>) {
    runApplication<InOrderOrdersApplication>(*args).close()
}

@Configuration
class AppConfig(
        @Value("\${queue.name}") val queueName: String,
        @Value("\${exchange.name}") val exchangeName: String
) {

    @Bean
    fun queue(): Queue = Queue(queueName, false)

    @Bean
    fun fanoutExchange(): FanoutExchange = FanoutExchange(exchangeName)

    @Bean
    fun binding(queue: Queue, fanoutExchange: FanoutExchange): Binding =
            BindingBuilder.bind(queue).to(fanoutExchange)
}