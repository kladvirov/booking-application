package by.kladvirov.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreationDto {

    @NotEmpty
    @Size(min = 2, max = 64, message = "Name has to be between 2 and 64 characters")
    private String name;

    @NotEmpty
    @Size(min = 2, max = 64, message = "Surname has to be between 2 and 64 characters")
    private String surname;

    @NotEmpty(message = "Login can't be empty")
    @Size(min = 2, max = 128, message = "Login has to be between 2 and 128 characters")
    private String login;

    @NotEmpty(message = "Password can't be empty")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "At least 8 characters, one uppercase letter, one lowercase " +
            "letter and one number are required")
    @Size(min = 8, max = 128, message = "Password has to be between 8 and 128 (this is too much, bro) characters")
    private String password;

    @NotEmpty(message = "Email can't be empty")
    @Email
    private String email;

    @NotNull(message = "Role ids are mandatory in order to create user")
    private Set<Long> roleIds;

}
