package by.kladvirov.service;

import by.kladvirov.dto.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    public void sendMessage(Message message) throws MessagingException {

        Context context = new Context();
        context.setVariable("message", message);

        String process = templateEngine.process(message.getTemplate(), context);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        helper.setTo(message.getEmail());
        helper.setSubject(message.getSubject());
        helper.setText(process, true);

        mailSender.send(mimeMessage);

    }

}