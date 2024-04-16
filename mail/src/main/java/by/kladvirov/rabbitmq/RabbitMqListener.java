package by.kladvirov.rabbitmq;

import by.kladvirov.dto.Message;
import by.kladvirov.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitMqListener {

    private final EmailService emailService;

    @RabbitListener(queues = {"${queue}"})
    public void emailListener(Message message) {
        log.info("Received: {}", message);
        emailService.sendMessage(message);
    }

}
