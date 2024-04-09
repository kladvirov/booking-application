package by.kladvirov.repository.redis;

import by.kladvirov.entity.redis.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByRefreshToken(String refreshToken);

    Optional<Token> findByToken(String token);

}
