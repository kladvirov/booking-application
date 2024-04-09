package by.kladvirov.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDto {

    @NotEmpty
    @Size(max = 512)
    private String token;

    @NotEmpty
    @Size(max = 512)
    private String refreshToken;

}
