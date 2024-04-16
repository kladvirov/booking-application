package by.kladvirov.service;

import by.kladvirov.entity.redis.Verification;

public interface VerificationService {

    Verification findByToken(String token);

    Verification findByUserId(Long id);

    Verification save(Verification verification);

    void deleteByUserId(Long id);

}
