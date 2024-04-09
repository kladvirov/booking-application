package by.kladvirov.service.impl;

import by.kladvirov.entity.redis.Token;
import by.kladvirov.repository.redis.TokenRepository;
import by.kladvirov.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Transactional(readOnly = true)
    @Override
    public Boolean existsByRefreshToken(String token) {
        return tokenRepository.existsByRefreshToken(token);
    }

    @Transactional
    @Override
    public void save(Token token) {
        tokenRepository.save(token);
    }

    @Transactional
    @Override
    public void deleteByToken(String token) {
        tokenRepository.deleteByToken(token);
    }

    @Transactional
    @Override
    public void deleteByRefreshToken(String token) {
        tokenRepository.deleteByRefreshToken(token);
    }

}
