package by.kladvirov.rabbitmq;

import by.kladvirov.dto.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMqPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${exchange}")
    private String exchange;

    @Value("${routing-key}")
    private String routingKey;

    public void send(Message message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

}
