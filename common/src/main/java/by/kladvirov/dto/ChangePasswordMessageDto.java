package by.kladvirov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ChangePasswordMessageDto implements Message{

    private String name;

    private String surname;

    private String email;

    private String resultMessage;

    private final String subject = "Change request";

    @Override
    public String getTemplate() {
        return "password-changing";
    }

}