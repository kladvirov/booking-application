package by.kladvirov.dto;

import by.kladvirov.enums.Status;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCreationDto {

    @NotNull(message = "Price cannot be empty")
    private Float pricePerHour;

    @NotEmpty(message = "Slot cannot be empty")
    @Size(min = 2, max = 32, message = "Slot name should be in diapason from 2 to 32 characters")
    private String slot;

    @NotNull(message = "Status cannot be empty")
    @Size(min = 2, max = 32, message = "Slot name should be in diapason from 2 to 32 characters")
    private Status status;

    @NotNull(message = "Creation date cannot be null")
    private ZonedDateTime createdAt;

    @Positive
    @NotNull
    private ZonedDateTime updatedAt;

    @Positive
    @NotNull
    private ZonedDateTime deletedAt;

    @NotNull(message = "Service provider cannot be empty")
    private Long serviceProviderId;

}
