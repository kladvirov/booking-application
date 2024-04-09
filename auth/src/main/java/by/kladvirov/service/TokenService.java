package by.kladvirov.service;

import by.kladvirov.entity.redis.Token;

public interface TokenService {

    Token findByRefreshToken(String refreshToken);

    Token findByAccessToken(String accessToken);

    void save(Token token);

    void delete(Token token);

}
