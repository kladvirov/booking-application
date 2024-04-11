package by.kladvirov;

import by.kladvirov.dto.ConfirmationMessageDto;
import by.kladvirov.dto.NotificationMessageDto;
import by.kladvirov.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Example {

    private final EmailService emailService;

    public void send() throws MessagingException {

        NotificationMessageDto notificationMessage = NotificationMessageDto.builder()
                .name("Vladislav")
                .surname("Kirov")
                .email("example1@gmail.com")
                .build();

        ConfirmationMessageDto confirmMessage = ConfirmationMessageDto.builder()
                .name("Andrey")
                .surname("Suslov")
                .email("example2@gmail.com").build();

        emailService.sendMessage(notificationMessage);
        emailService.sendMessage(confirmMessage);
    }

}