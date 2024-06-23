package by.kladvirov.dto.core;

import by.kladvirov.enums.Status;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceProviderDto {

    @NotEmpty(message = "Name cannot be null")
    private String name;

    @NotEmpty(message = "Location cannot be empty")
    private String location;

    @NotEmpty(message = "Type cannot be empty")
    private String type;

    @NotNull(message = "Status cannot be null")
    @Size(min = 2, max = 64, message = "Status name should be in diapason from 2 to 64 characters")
    private Status status;

    @NotNull(message = "Creation date cannot be null")
    private ZonedDateTime createdAt;

}