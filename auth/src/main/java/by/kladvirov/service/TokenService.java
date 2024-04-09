package by.kladvirov.service;

import by.kladvirov.entity.redis.Token;

public interface TokenService {

    Boolean existsByRefreshToken(String token);

    void save(Token token);

    void deleteByToken(String token);

    void deleteByRefreshToken(String token);

}
