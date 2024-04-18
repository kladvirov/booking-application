package by.kladvirov.service.impl;

import by.kladvirov.dto.UserDto;
import by.kladvirov.entity.redis.Verification;
import by.kladvirov.exception.ServiceException;
import by.kladvirov.repository.redis.VerificationRepository;
import by.kladvirov.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    private final VerificationRepository verificationRepository;

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
    public void deleteByUserId(Long id) {
        verificationRepository.deleteByUserId(id);
    }

    private Verification buildVerification(Long userId) {
        String token = UUID.randomUUID().toString();
        return Verification.builder()
                .token(token)
                .userId(userId)
                .createdAt(ZonedDateTime.now().toString())
                .build();
    }

}
