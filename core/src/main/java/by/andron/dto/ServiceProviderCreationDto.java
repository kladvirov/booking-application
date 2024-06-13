package by.andron.dto;

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
public class ServiceProviderCreationDto {

    @NotEmpty(message = "Name cannot be null")
    private String name;

    @NotEmpty(message = "Location cannot be empty")
    private String location;

    @NotEmpty(message = "Type cannot be empty")
    private String type;

    @NotNull(message = "Status cannot be empty")
    @Size(min = 2, max = 64, message = "Status name should be in diapason from 2 to 64 characters")
    private Status status;

    @NotNull(message = "Creation date cannot be empty")
    private ZonedDateTime createdAt;

    @NotNull
    private ZonedDateTime updatedAt;

    @NotNull
    private ZonedDateTime deletedAt;

}
