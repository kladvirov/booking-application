package by.kladvirov.entity.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {

    @Id
    private Long id;

    private String token;

    private String refreshToken;

    private Long userId;

}
