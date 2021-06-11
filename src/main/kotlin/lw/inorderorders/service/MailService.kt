package lw.inorderorders.service

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class MailService {
    @RabbitListener(queues = ["\${queue.name}"])
    fun handleMessage(message: String) {
        println(message)
    }
}