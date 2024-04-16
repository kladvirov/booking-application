package by.kladvirov.service.impl;

import by.kladvirov.entity.redis.Token;
import by.kladvirov.exception.ServiceException;
import by.kladvirov.repository.redis.TokenRepository;
import by.kladvirov.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Transactional(readOnly = true)
    @Override
    public Token findByRefreshToken(String refreshToken) {
        return tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new ServiceException("There is no such token", HttpStatus.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    @Override
    public Token findByAccessToken(String accessToken) {
        return tokenRepository.findByToken(accessToken)
                .orElseThrow(() -> new ServiceException("There is no such token", HttpStatus.NOT_FOUND));
    }

    @Transactional
    @Override
    public void save(Token token) {
        tokenRepository.save(token);
    }

    @Transactional
    @Override
    public void delete(Token token) {
        tokenRepository.delete(token);
    }

}
