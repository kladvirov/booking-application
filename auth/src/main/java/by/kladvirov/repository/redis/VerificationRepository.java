package by.kladvirov.repository.redis;

import by.kladvirov.entity.redis.Verification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationRepository extends JpaRepository<Verification, Long> {

    Optional<Verification> findByToken(String token);

    Optional<Verification> findByUserId(Long id);

    void deleteByUserId(Long id);

}
