package by.kladvirov.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordChangingDto {

    @NotEmpty(message = "Password can't be empty")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "At least 8 characters, one uppercase letter, one lowercase " +
            "letter and one number are required")
    @Size(min = 8, max = 128, message = "Password has to be between 8 and 128 (this is too much, bro) characters")
    private String currentPassword;

    @NotEmpty(message = "Password can't be empty")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "At least 8 characters, one uppercase letter, one lowercase " +
            "letter and one number are required")
    @Size(min = 8, max = 128, message = "Password has to be between 8 and 128 (this is too much, bro) characters")
    private String newPassword;

    @NotEmpty(message = "Password can't be empty")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "At least 8 characters, one uppercase letter, one lowercase " +
            "letter and one number are required")
    @Size(min = 8, max = 128, message = "Password has to be between 8 and 128 (this is too much, bro) characters")
    private String confirmationPassword;

}