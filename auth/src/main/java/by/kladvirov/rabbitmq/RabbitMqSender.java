package by.kladvirov.rabbitmq;

import by.kladvirov.dto.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMqSender {

    private final RabbitTemplate rabbitTemplate;

    public void send(Message message) {
        log.info("Sending message..");
        rabbitTemplate.convertAndSend("email_exchange", "email_key", message);
    }
}
