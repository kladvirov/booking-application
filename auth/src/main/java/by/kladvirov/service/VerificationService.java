package by.kladvirov.service;

import by.kladvirov.entity.redis.Verification;

public interface VerificationService {

    Verification findByToken(String token);

    Verification findByUserId(Long id);

    Verification save(Long userId);

    void sendVerificationMessage(String username);

    void deleteByUserId(Long id);

    void verifyUser(String token);

}
