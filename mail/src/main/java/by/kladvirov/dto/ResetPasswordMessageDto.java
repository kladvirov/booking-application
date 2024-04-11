package by.kladvirov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ResetPasswordMessageDto implements Message{

    private String name;

    private String surname;

    private String email;

    private String url;

    private final String subject = "Reset password request";

    @Override
    public String getTemplate() {
        return "reset-password";
    }

}
