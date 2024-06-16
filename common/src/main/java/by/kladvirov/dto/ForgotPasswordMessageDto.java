package by.kladvirov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ForgotPasswordMessageDto implements Message{

    private String name;

    private String surname;

    private String email;

    private final String subject = "Forgot password";

    private final String baseUrl = "http://localhost:8081/auth/restore-password";

    @Override
    public String getTemplate() {
        return "forgot-password";
    }

}