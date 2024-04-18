package by.kladvirov.entity.redis;

import by.kladvirov.enums.UserStatus;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@RedisHash(timeToLive = 604800000L)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Verification {

    @Id
    private Long id;

    @Indexed
    private String token;

    private Long userId;

    private String createdAt;

}
