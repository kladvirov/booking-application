package by.kladvirov.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorityDto {

    private Long id;

    @NotEmpty(message = "Authority can't be empty")
    private String name;

}
