package by.kladvirov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ConfirmationMessageDto implements Message{

    private String name;

    private String surname;

    private String email;

    private String url;

    private final String subject = "Confirmation request";

    @Override
    public String getTemplate() {
        return "confirmation";
    }

}
