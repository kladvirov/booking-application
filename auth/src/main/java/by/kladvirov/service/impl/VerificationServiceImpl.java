package by.kladvirov.service.impl;

import by.kladvirov.dto.ConfirmationMessageDto;
import by.kladvirov.dto.Message;
import by.kladvirov.entity.User;
import by.kladvirov.entity.redis.Verification;
import by.kladvirov.enums.UserStatus;
import by.kladvirov.exception.ServiceException;
import by.kladvirov.rabbitmq.RabbitMqPublisher;
import by.kladvirov.repository.redis.VerificationRepository;
import by.kladvirov.service.UserService;
import by.kladvirov.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    private final VerificationRepository verificationRepository;

    private final UserService userService;

    private final RabbitMqPublisher publisher;

    @Value("${verification.duration}")
    private Long duration;

    @Transactional(readOnly = true)
    @Override
    public Verification findByToken(String token) {
        return verificationRepository.findByToken(token)
                .orElseThrow(() -> new ServiceException("There is no such token", HttpStatus.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    @Override
    public Verification findByUserId(Long id) {
        return verificationRepository.findByUserId(id)
                .orElseThrow(() -> new ServiceException("There is no such token", HttpStatus.NOT_FOUND));
    }

    @Transactional
    @Override
    public Verification save(Long userId) {
        return verificationRepository.save(buildVerification(userId));
    }

    @Transactional
    @Override
    public void sendVerificationMessage(String username) {
        User user = userService.getByLogin(username);
        Verification verification = findByUserId(user.getId());
        if (user.getStatus() == UserStatus.UNVERIFIED && isVerificationDurationNotEarly(verification)) {
            deleteByUserId(user.getId());
            Verification newVerification = save(user.getId());
            Message message = new ConfirmationMessageDto(user.getName(), user.getSurname(), user.getEmail(), newVerification.getToken());
            publisher.send(message);
        } else {
            throw new ServiceException("There was an exception during sending message");
        }
    }

    @Transactional
    @Override
    public void deleteByUserId(Long id) {
        verificationRepository.deleteByUserId(id);
    }

    @Transactional
    @Override
    public void verifyUser(String token) {
        Verification verification = findByToken(token);
        userService.updateStatus(verification.getUserId(), UserStatus.VERIFIED);
    }

    private Verification buildVerification(Long userId) {
        String token = UUID.randomUUID().toString();
        return Verification.builder()
                .token(token)
                .userId(userId)
                .createdAt(ZonedDateTime.now().toString())
                .build();
    }

    private boolean isVerificationDurationNotEarly(Verification verificationToken) {
        return ChronoUnit.SECONDS.between(ZonedDateTime.now(), ZonedDateTime.parse(verificationToken.getCreatedAt())) > duration;
    }

}
