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
public class Verification {

    @Id
    private Long id;

    @Indexed
    private String token;

    private Long userId;

    private String createdAt;

}
