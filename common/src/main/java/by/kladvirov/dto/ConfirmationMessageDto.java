package by.kladvirov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ConfirmationMessageDto implements Message{

    private String name;

    private String surname;

    private String email;

    private String token;

    private final String subject = "Confirmation request";

    private final String baseUrl = "http://localhost:8081/auth/verify";

    @Override
    public String getTemplate() {
        return "confirmation";
    }

}