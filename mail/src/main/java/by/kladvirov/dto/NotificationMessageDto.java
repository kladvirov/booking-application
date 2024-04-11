package by.kladvirov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class NotificationMessageDto implements Message{

    private String name;

    private String surname;

    private String email;

    private final String subject = "Notification";

    @Override
    public String getTemplate() {
        return "notification";
    }

}