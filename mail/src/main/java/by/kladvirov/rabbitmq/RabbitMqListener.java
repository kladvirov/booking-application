package by.kladvirov.rabbitmq;

import by.kladvirov.dto.Message;
import by.kladvirov.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMqListener {

    private final EmailService emailService;

    @RabbitListener(queues = {"${queue}"})
    public void emailListener(Message message) {
        emailService.sendMessage(message);
    }

}
