package by.kladvirov.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;

    @NotEmpty
    @Size(min = 2, max = 64, message = "Name has to be between 2 and 64 characters")
    private String name;

    @NotEmpty
    @Size(min = 2, max = 64, message = "Surname has to be between 2 and 64 characters")
    private String surname;

    @NotEmpty(message = "Login can't be empty")
    @Size(min = 2, max = 128, message = "Login has to be between 2 and 128 characters")
    private String login;

    @NotEmpty(message = "Email can't be empty")
    @Email
    private String email;

    @NotNull(message = "Balance can't be null")
    private BigDecimal balance;

}
