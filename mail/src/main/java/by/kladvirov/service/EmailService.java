package by.kladvirov.service;

import by.kladvirov.dto.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.naming.ServiceUnavailableException;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    public void sendMessage(Message message) {

        Context context = new Context();
        context.setVariable("message", message);

        String process = templateEngine.process(message.getTemplate(), context);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try {
            helper.setTo(message.getEmail());
            helper.setSubject(message.getSubject());
            helper.setText(process, true);
        } catch (MessagingException | MailException exception) {
           exception.printStackTrace();
        }

        mailSender.send(mimeMessage);

    }

}