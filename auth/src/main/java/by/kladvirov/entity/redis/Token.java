package by.kladvirov.entity.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(timeToLive = 604800000L)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {

    @Id
    private Long id;

    @Indexed
    private String token;

    @Indexed
    private String refreshToken;

    private Long userId;

}
