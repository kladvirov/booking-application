package by.kladvirov.repository.redis;

import by.kladvirov.entity.redis.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Boolean existsByRefreshToken(String token);

    @Modifying
    void deleteByToken(String token);

    @Modifying
    void deleteByRefreshToken(String token);

}
